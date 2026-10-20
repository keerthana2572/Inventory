package com.inventory.ims.strategy;

import com.inventory.ims.model.PriceChangeDirection;

import java.math.BigDecimal;

public class PricingRecommendation {
    private BigDecimal recommendedPrice;
    private PriceChangeDirection direction;
    private Double confidence;
    private String reasoning;

    public PricingRecommendation() {}

    public PricingRecommendation(BigDecimal recommendedPrice, PriceChangeDirection direction, Double confidence, String reasoning) {
        this.recommendedPrice = recommendedPrice;
        this.direction = direction;
        this.confidence = confidence;
        this.reasoning = reasoning;
    }

    // Getters and Setters
    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public PriceChangeDirection getDirection() {
        return direction;
    }

    public void setDirection(PriceChangeDirection direction) {
        this.direction = direction;
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
}