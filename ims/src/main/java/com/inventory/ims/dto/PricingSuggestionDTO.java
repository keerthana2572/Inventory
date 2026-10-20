package com.inventory.ims.dto;

import com.inventory.ims.model.PriceChangeDirection;
import com.inventory.ims.model.SuggestionStatus;
import com.inventory.ims.model.TriggerReason;
import java.math.BigDecimal;

public class PricingSuggestionDTO {
    private String id;
    private String productId;
    private BigDecimal currentPrice;
    private BigDecimal recommendedPrice;
    private PriceChangeDirection changeDirection;
    private Double confidence;
    private String reasoning;
    private SuggestionStatus status;
    private TriggerReason triggerReason;
    
    // Constructors
    public PricingSuggestionDTO() {}
    
    public PricingSuggestionDTO(String id, String productId, BigDecimal currentPrice, BigDecimal recommendedPrice,
                                PriceChangeDirection changeDirection, Double confidence, String reasoning,
                                SuggestionStatus status, TriggerReason triggerReason) {
        this.id = id;
        this.productId = productId;
        this.currentPrice = currentPrice;
        this.recommendedPrice = recommendedPrice;
        this.changeDirection = changeDirection;
        this.confidence = confidence;
        this.reasoning = reasoning;
        this.status = status;
        this.triggerReason = triggerReason;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
    
    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }
    
    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }
    
    public PriceChangeDirection getChangeDirection() {
        return changeDirection;
    }
    
    public void setChangeDirection(PriceChangeDirection changeDirection) {
        this.changeDirection = changeDirection;
    }
    
    public Double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
    
    public String getReasoning() {
        return reasoning;
    }
    
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
    
    public SuggestionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SuggestionStatus status) {
        this.status = status;
    }
    
    public TriggerReason getTriggerReason() {
        return triggerReason;
    }
    
    public void setTriggerReason(TriggerReason triggerReason) {
        this.triggerReason = triggerReason;
    }
}