package com.inventory.ims.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pricing_suggestions")
public class PricingSuggestion {
    @Id
    private String id;
    
    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "current_price", precision = 19, scale = 4)
    private BigDecimal currentPrice;
    
    @Column(name = "recommended_price", precision = 19, scale = 4)
    private BigDecimal recommendedPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "change_direction")
    private PriceChangeDirection changeDirection;
    
    @Column(name = "confidence")
    private Double confidence;
    
    @Column(name = "reasoning", columnDefinition = "TEXT")
    private String reasoning;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SuggestionStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_reason")
    private TriggerReason triggerReason;
    
    // Constructors
    public PricingSuggestion() {}
    
    public PricingSuggestion(String id, String productId, BigDecimal currentPrice, BigDecimal recommendedPrice,
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
    
    @Override
    public String toString() {
        return "PricingSuggestion{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", currentPrice=" + currentPrice +
                ", recommendedPrice=" + recommendedPrice +
                ", changeDirection=" + changeDirection +
                ", confidence=" + confidence +
                ", reasoning='" + reasoning + '\'' +
                ", status=" + status +
                ", triggerReason=" + triggerReason +
                '}';
    }
}