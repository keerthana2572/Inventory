package com.inventory.ims.strategy;

import java.math.BigDecimal;

public class AIResponse {
    private BigDecimal price;
    private int quantity;
    private Integer leadTime;
    private double confidence;
    private String reasoning;
    
    // Getters and setters
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Integer getLeadTime() {
        return leadTime;
    }
    
    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }
    
    public double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    
    public String getReasoning() {
        return reasoning;
    }
    
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }
}