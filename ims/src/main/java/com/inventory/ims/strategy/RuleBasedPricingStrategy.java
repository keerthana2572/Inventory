package com.inventory.ims.strategy;

import com.inventory.ims.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component("ruleBased")
public class RuleBasedPricingStrategy implements CommerceStrategy {
    
    // Threshold for considering demand velocity as "high"
    // This should ideally be configurable or calculated based on category averages
    private static final int HIGH_DEMAND_VELOCITY_THRESHOLD = 10;
    
    @Override
    public CommerceRecommendation generateRecommendations(Product product, TriggerReason triggerReason) {
        // Generate pricing recommendation
        PricingRecommendation pricing = generatePricingRecommendation(product, triggerReason);
        
        // Generate reorder recommendation
        ReorderRecommendation reorder = generateReorderRecommendation(product, triggerReason);
        
        // Return combined recommendation
        return new CommerceRecommendation(pricing, reorder, triggerReason);
    }
    
    private PricingRecommendation generatePricingRecommendation(Product product, TriggerReason triggerReason) {
        BigDecimal currentPrice = product.getCurrentPrice();
        BigDecimal recommendedPrice = currentPrice;
        PriceChangeDirection direction = PriceChangeDirection.HOLD;
        
        // If stock is below reorder threshold, recommend 10% price increase
        if (product.getStockLevel() < product.getReorderThreshold()) {
            recommendedPrice = currentPrice.multiply(BigDecimal.valueOf(1.10));
            direction = PriceChangeDirection.INCREASE;
        }
        // If demand velocity is more than 2x category average, recommend 5% increase
        else if (isHighDemandVelocity(product)) {
            recommendedPrice = currentPrice.multiply(BigDecimal.valueOf(1.05));
            direction = PriceChangeDirection.INCREASE;
        }
        
        return new PricingRecommendation(
            recommendedPrice,
            direction,
            1.0, // High confidence for rule-based strategy
            generatePricingReasoning(product, direction, triggerReason)
        );
    }
    
    private ReorderRecommendation generateReorderRecommendation(Product product, TriggerReason triggerReason) {
        // Recommend quantity = (reorder threshold × 3) − current stock, minimum 1
        int recommendedQuantity = Math.max(1, (product.getReorderThreshold() * 3) - product.getStockLevel());
        
        return new ReorderRecommendation(
            recommendedQuantity,
            null, // Lead time is optional
            1.0, // High confidence for rule-based strategy
            generateReorderReasoning(product, recommendedQuantity, triggerReason)
        );
    }
    
    private boolean isHighDemandVelocity(Product product) {
        // This is a simplified implementation
        // In a real implementation, we would compare against actual category averages
        // For now, we use a fixed threshold
        return product.getDemandVelocity() > HIGH_DEMAND_VELOCITY_THRESHOLD;
    }
    
    private String generatePricingReasoning(Product product, PriceChangeDirection direction, TriggerReason triggerReason) {
        StringBuilder reasoning = new StringBuilder();
        
        switch (triggerReason) {
            case INVENTORY_LOW:
                reasoning.append("Stock level (").append(product.getStockLevel())
                        .append(") is below reorder threshold (").append(product.getReorderThreshold()).append("). ");
                break;
            case DEMAND_SPIKE:
                reasoning.append("Demand velocity (").append(product.getDemandVelocity())
                        .append(") indicates a spike in demand. ");
                break;
            default:
                reasoning.append("Regular pricing review. ");
        }
        
        switch (direction) {
            case INCREASE:
                reasoning.append("Recommend increasing price to optimize revenue.");
                break;
            case DECREASE:
                reasoning.append("Recommend decreasing price to stimulate demand.");
                break;
            case HOLD:
                reasoning.append("Current price is appropriate for market conditions.");
                break;
        }
        
        return reasoning.toString();
    }
    
    private String generateReorderReasoning(Product product, int recommendedQuantity, TriggerReason triggerReason) {
        StringBuilder reasoning = new StringBuilder();
        
        switch (triggerReason) {
            case INVENTORY_LOW:
                reasoning.append("Stock level (").append(product.getStockLevel())
                        .append(") is below reorder threshold (").append(product.getReorderThreshold()).append(". ");
                break;
            case DEMAND_SPIKE:
                reasoning.append("Increased demand requires additional inventory. ");
                break;
            default:
                reasoning.append("Regular inventory replenishment. ");
        }
        
        reasoning.append("Recommended reorder quantity: ").append(recommendedQuantity).append(" units.");
        
        return reasoning.toString();
    }
}