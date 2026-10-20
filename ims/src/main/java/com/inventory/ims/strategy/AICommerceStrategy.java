package com.inventory.ims.strategy;

import com.inventory.ims.ai.AIGateway;
import com.inventory.ims.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("ai")
public class AICommerceStrategy implements CommerceStrategy {
    
    private final AIGateway aiGateway;
    private final CategoryAverageCalculator categoryAverageCalculator;
    
    @Autowired
    public AICommerceStrategy(AIGateway aiGateway, CategoryAverageCalculator categoryAverageCalculator) {
        this.aiGateway = aiGateway;
        this.categoryAverageCalculator = categoryAverageCalculator;
    }
    
    @Override
    public CommerceRecommendation generateRecommendations(Product product, TriggerReason triggerReason) {
        try {
            // Calculate category average for demand velocity
            double categoryAverage = categoryAverageCalculator.getCategoryAverage(product.getCategory());
            
            // Generate pricing recommendation
            PricingRecommendation pricing = generatePricingRecommendation(product, triggerReason, categoryAverage);
            
            // Generate reorder recommendation
            ReorderRecommendation reorder = generateReorderRecommendation(product, triggerReason, categoryAverage);
            
    private PricingRecommendation generatePricingRecommendation(Product product, TriggerReason triggerReason, double categoryAverage) {
        try {
            String prompt = createPricingPrompt(product, triggerReason, categoryAverage);
            String jsonResponse = aiGateway.callLLM(prompt);
            
            // Parse and validate the response
            AIResponse response = parseAIResponse(jsonResponse, "pricing");
            
            // Create pricing recommendation with validation
            PricingRecommendation pricing = new PricingRecommendation();
            pricing.setRecommendedPrice(validatePrice(response.getPrice(), product.getCurrentPrice()));
            pricing.setDirection(determinePriceDirection(product.getCurrentPrice(), pricing.getRecommendedPrice()));
            pricing.setConfidence(response.getConfidence());
            pricing.setReasoning(response.getReasoning());
            
            return pricing;
        } catch (Exception e) {
            // Fallback to rule-based pricing
            return createRuleBasedPricingRecommendation(product, triggerReason);
        }
    private String createPricingPrompt(Product product, TriggerReason triggerReason, double categoryAverage) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an expert retail merchandiser and pricing strategist. Analyze this product and provide pricing recommendations.\n\n");
        prompt.append("Product Information:\n");
        prompt.append("- Name: ").append(product.getName()).append("\n");
        prompt.append("- Category: ").append(product.getCategory()).append("\n");
        prompt.append("- Current Price: $").append(product.getCurrentPrice()).append("\n");
        prompt.append("- Stock Level: ").append(product.getStockLevel()).append("\n");
        prompt.append("- Reorder Threshold: ").append(product.getReorderThreshold()).append("\n");
        prompt.append("- Demand Velocity: ").append(product.getDemandVelocity()).append(" units in last 24h\n");
        prompt.append("- Category Average Demand: ").append(String.format("%.2f", categoryAverage)).append(" units in last 24h\n\n");
        
        prompt.append("Trigger Context: ").append(triggerReason).append("\n\n");
        
        prompt.append("Instructions:\n");
        prompt.append("1. Analyze the trigger context and current situation\n");
        prompt.append("2. Recommend a new price considering inventory levels, demand trends, and category benchmarks\n");
        prompt.append("3. Explain your reasoning clearly\n");
        prompt.append("4. Provide a confidence score between 0.0 and 1.0\n\n");
        
        switch (triggerReason) {
            case INVENTORY_LOW:
                prompt.append("Special considerations for INVENTORY_LOW trigger:\n");
                prompt.append("- Stock level (").append(product.getStockLevel()).append(") is below reorder threshold (")
                      .append(product.getReorderThreshold()).append(")\n");
                prompt.append("- Consider raising price to preserve limited inventory OR offering clearance to move units quickly\n");
                prompt.append("- Explain the tradeoffs in your reasoning\n\n");
                break;
            case DEMAND_SPIKE:
                prompt.append("Special considerations for DEMAND_SPIKE trigger:\n");
    private String createReorderPrompt(Product product, TriggerReason triggerReason, double categoryAverage) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an expert supply chain manager and inventory planner. Analyze this product and provide reorder recommendations.\n\n");
        prompt.append("Product Information:\n");
        prompt.append("- Name: ").append(product.getName()).append("\n");
        prompt.append("- Category: ").append(product.getCategory()).append("\n");
        prompt.append("- Current Price: $").append(product.getCurrentPrice()).append("\n");
        prompt.append("- Stock Level: ").append(product.getStockLevel()).append("\n");
        prompt.append("- Reorder Threshold: ").append(product.getReorderThreshold()).append("\n");
        prompt.append("- Demand Velocity: ").append(product.getDemandVelocity()).append(" units in last 24h\n");
        prompt.append("- Category Average Demand: ").append(String.format("%.2f", categoryAverage)).append(" units in last 24h\n\n");
        
        prompt.append("Trigger Context: ").append(triggerReason).append("\n\n");
        
        prompt.append("Instructions:\n");
        prompt.append("1. Analyze the trigger context and current inventory situation\n");
        prompt.append("2. Recommend a reorder quantity considering demand forecasts and safety stock\n");
        prompt.append("3. Explain your reasoning clearly\n");
        prompt.append("4. Provide a confidence score between 0.0 and 1.0\n\n");
        
        switch (triggerReason) {
            case INVENTORY_LOW:
                prompt.append("Special considerations for INVENTORY_LOW trigger:\n");
                prompt.append("- Immediate replenishment needed\n");
                prompt.append("- Consider higher quantity to rebuild buffer stock\n\n");
                break;
            case DEMAND_SPIKE:
                prompt.append("Special considerations for DEMAND_SPIKE trigger:\n");
                prompt.append("- Anticipate continued high demand\n");
                prompt.append("- Consider increased reorder quantity to avoid stockouts\n\n");
    private AIResponse parseAIResponse(String jsonResponse, String type) throws Exception {
        // Basic JSON parsing - in production, use a proper JSON library
        AIResponse response = new AIResponse();
        
        // Remove any markdown formatting if present
        if (jsonResponse.startsWith("```json")) {
            jsonResponse = jsonResponse.substring(7);
        }
        if (jsonResponse.endsWith("```")) {
            jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 3);
        }
        
        // Simple parsing for demonstration - in production, use Jackson or Gson
        try {
            // This is a simplified parser for demonstration
            // In production, use proper JSON parsing
            if (type.equals("pricing")) {
                // Extract price, confidence, reasoning
                response.setPrice(extractDouble(jsonResponse, "price"));
                response.setConfidence(extractDouble(jsonResponse, "confidence"));
                response.setReasoning(extractString(jsonResponse, "reasoning"));
            } else if (type.equals("reorder")) {
                // Extract quantity, confidence, reasoning, leadTime
                response.setQuantity(extractInt(jsonResponse, "quantity"));
                response.setConfidence(extractDouble(jsonResponse, "confidence"));
                response.setReasoning(extractString(jsonResponse, "reasoning"));
                response.setLeadTime(extractInteger(jsonResponse, "leadTime"));
            }
        } catch (Exception e) {
            throw new Exception("Failed to parse AI response: " + e.getMessage());
        }
        
        return response;
    }
    
    // Helper methods for simple JSON parsing
    private double extractDouble(String json, String field) {
        String value = extractString(json, field);
        if (value != null && !value.isEmpty()) {
            try {
                return Double.parseDouble(value.replaceAll("[^0-9.]", ""));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
    
    private int extractInt(String json, String field) {
        String value = extractString(json, field);
        if (value != null && !value.isEmpty()) {
            try {
                return Integer.parseInt(value.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
    
    private Integer extractInteger(String json, String field) {
        String value = extractString(json, field);
        if (value != null && !value.equals("null") && !value.isEmpty()) {
            try {
                return Integer.parseInt(value.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    private CommerceRecommendation fallbackToRuleBased(Product product, TriggerReason triggerReason) {
        // Use rule-based strategy as fallback
        RuleBasedPricingStrategy ruleBasedStrategy = new RuleBasedPricingStrategy();
        return ruleBasedStrategy.generateRecommendations(product, triggerReason);
    }
    
    private PricingRecommendation createRuleBasedPricingRecommendation(Product product, TriggerReason triggerReason) {
        // Rule-based pricing logic from RuleBasedPricingStrategy
        PricingRecommendation pricing = new PricingRecommendation();
        pricing.setConfidence(1.0);
        
        boolean stockLow = product.getStockLevel() < product.getReorderThreshold();
        boolean demandHigh = product.getDemandVelocity() > 10; // Simplified threshold
        
        if (stockLow) {
            pricing.setRecommendedPrice(product.getCurrentPrice().multiply(new BigDecimal("1.10")).setScale(2, RoundingMode.HALF_UP));
            pricing.setDirection(PriceChangeDirection.INCREASE);
            pricing.setReasoning("Rule-based recommendation: Stock level (" + product.getStockLevel() + 
                               ") is below reorder threshold (" + product.getReorderThreshold() + 
                               "). Increasing price by 10% to protect inventory.");
        } else if (demandHigh) {
            pricing.setRecommendedPrice(product.getCurrentPrice().multiply(new BigDecimal("1.05")).setScale(2, RoundingMode.HALF_UP));
            pricing.setDirection(PriceChangeDirection.INCREASE);
            pricing.setReasoning("Rule-based recommendation: Demand velocity (" + product.getDemandVelocity() + 
                               ") indicates high demand. Increasing price by 5% to capitalize on popularity.");
        } else {
            pricing.setRecommendedPrice(product.getCurrentPrice());
            pricing.setDirection(PriceChangeDirection.HOLD);
            pricing.setReasoning("Rule-based recommendation: Regular pricing review. Current price maintained as appropriate for market conditions.");
        }
        
        return pricing;
    }
    
    private ReorderRecommendation createRuleBasedReorderRecommendation(Product product) {
        // Rule-based reorder logic from RuleBasedPricingStrategy
        ReorderRecommendation reorder = new ReorderRecommendation();
        reorder.setConfidence(1.0);
        
        int recommendedQuantity = (product.getReorderThreshold() * 3) - product.getStockLevel();
        recommendedQuantity = Math.max(1, recommendedQuantity); // Ensure minimum of 1
        
        reorder.setRecommendedQuantity(recommendedQuantity);
        reorder.setReasoning("Rule-based recommendation: Reorder quantity calculated as (reorder threshold × 3) − current stock = (" + 
                           product.getReorderThreshold() + " × 3) − " + product.getStockLevel() + " = " + recommendedQuantity + 
                           ". This ensures adequate buffer stock.");
        reorder.setSuggestedLeadTimeDays(null); // Optional field
        
        return reorder;
    }
}
    }
    
    private String extractString(String json, String field) {
        int startIndex = json.indexOf("\"" + field + "\"");
        if (startIndex == -1) return "";
        
        startIndex = json.indexOf(":", startIndex) + 1;
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }
        
        if (startIndex >= json.length()) return "";
        
        char quote = json.charAt(startIndex);
        if (quote == '"' || quote == '\'') {
            startIndex++;
            int endIndex = json.indexOf(quote, startIndex);
            if (endIndex == -1) return "";
            return json.substring(startIndex, endIndex);
        } else {
            // Handle unquoted values (numbers)
            int endIndex = startIndex;
            while (endIndex < json.length() && json.charAt(endIndex) != ',' && json.charAt(endIndex) != '}') {
                endIndex++;
            }
            return json.substring(startIndex, endIndex).trim();
        }
    }
    
    private BigDecimal validatePrice(BigDecimal recommendedPrice, BigDecimal currentPrice) {
        if (recommendedPrice == null || recommendedPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return currentPrice;
        }
        
        // Check if price is within reasonable bounds (not more than 10x current price)
        BigDecimal maxPrice = currentPrice.multiply(new BigDecimal("10"));
        if (recommendedPrice.compareTo(maxPrice) > 0) {
            return maxPrice; // Cap at 10x current price
        }
        
        return recommendedPrice.setScale(2, RoundingMode.HALF_UP);
    }
    
    private int validateQuantity(int recommendedQuantity) {
        // Ensure positive quantity
        return Math.max(1, recommendedQuantity);
    }
    
    private PriceChangeDirection determinePriceDirection(BigDecimal currentPrice, BigDecimal recommendedPrice) {
        BigDecimal difference = recommendedPrice.subtract(currentPrice);
        BigDecimal threshold = currentPrice.multiply(new BigDecimal("0.01")); // 1% threshold
        
        if (difference.compareTo(threshold) > 0) {
            return PriceChangeDirection.INCREASE;
        } else if (difference.compareTo(threshold.negate()) < 0) {
            return PriceChangeDirection.DECREASE;
        } else {
            return PriceChangeDirection.HOLD;
        }
    }
                break;
            case MANUAL:
                prompt.append("Special considerations for MANUAL trigger:\n");
                prompt.append("- Regular inventory review\n");
                prompt.append("- Balance optimal stock levels with carrying costs\n\n");
                break;
            default:
                prompt.append("General inventory analysis\n\n");
                break;
        }
        
        prompt.append("Respond in this exact JSON format:\n");
        prompt.append("{\n");
        prompt.append("  \"quantity\": <integer>,\n");
        prompt.append("  \"confidence\": <number between 0.0 and 1.0>,\n");
        prompt.append("  \"reasoning\": <string explaining your recommendation>,\n");
        prompt.append("  \"leadTime\": <optional integer for suggested lead time in days>\n");
        prompt.append("}");
        
        return prompt.toString();
    }
                prompt.append("- Demand velocity (").append(product.getDemandVelocity())
                      .append(") is significantly above category average (").append(String.format("%.2f", categoryAverage)).append(")\n");
                prompt.append("- Consider modest price increase to capitalize on popularity while maintaining demand\n\n");
                break;
            case MANUAL:
                prompt.append("Special considerations for MANUAL trigger:\n");
                prompt.append("- Regular pricing review requested\n");
                prompt.append("- Consider all factors: inventory levels, demand trends, and competitor positioning\n\n");
                break;
            default:
                prompt.append("General pricing analysis\n\n");
                break;
        }
        
        prompt.append("Respond in this exact JSON format:\n");
        prompt.append("{\n");
        prompt.append("  \"price\": <number>,\n");
        prompt.append("  \"confidence\": <number between 0.0 and 1.0>,\n");
        prompt.append("  \"reasoning\": <string explaining your recommendation>\n");
        prompt.append("}");
        
        return prompt.toString();
    }
    }
    
    private ReorderRecommendation generateReorderRecommendation(Product product, TriggerReason triggerReason, double categoryAverage) {
        try {
            String prompt = createReorderPrompt(product, triggerReason, categoryAverage);
            String jsonResponse = aiGateway.callLLM(prompt);
            
            // Parse and validate the response
            AIResponse response = parseAIResponse(jsonResponse, "reorder");
            
            // Create reorder recommendation with validation
            ReorderRecommendation reorder = new ReorderRecommendation();
            reorder.setRecommendedQuantity(validateQuantity(response.getQuantity()));
            reorder.setConfidence(response.getConfidence());
            reorder.setReasoning(response.getReasoning());
            reorder.setSuggestedLeadTimeDays(response.getLeadTime());
            
            return reorder;
        } catch (Exception e) {
            // Fallback to rule-based reorder
            return createRuleBasedReorderRecommendation(product);
        }
    }
            // Create and return the combined recommendation
            CommerceRecommendation recommendation = new CommerceRecommendation();
            recommendation.setPricing(pricing);
            recommendation.setReorder(reorder);
            recommendation.setTriggerReason(triggerReason);
            
            return recommendation;
        } catch (Exception e) {
            // Fallback to rule-based strategy if AI fails
            return fallbackToRuleBased(product, triggerReason);
        }
    }
}