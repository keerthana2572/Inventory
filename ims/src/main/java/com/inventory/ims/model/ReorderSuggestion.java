package com.inventory.ims.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reorder_suggestions")
public class ReorderSuggestion {
    @Id
    private String id;
    
    @Column(name = "product_id")
    private String productId;
    
    @Column(name = "current_stock")
    private Integer currentStock;
    
    @Column(name = "recommended_quantity")
    private Integer recommendedQuantity;
    
    @Column(name = "suggested_lead_time_days")
    private Integer suggestedLeadTimeDays;
    
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
    public ReorderSuggestion() {}
    
    public ReorderSuggestion(String id, String productId, Integer currentStock, Integer recommendedQuantity,
                             Integer suggestedLeadTimeDays, Double confidence, String reasoning,
                             SuggestionStatus status, TriggerReason triggerReason) {
        this.id = id;
        this.productId = productId;
        this.currentStock = currentStock;
        this.recommendedQuantity = recommendedQuantity;
        this.suggestedLeadTimeDays = suggestedLeadTimeDays;
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
    
    public Integer getCurrentStock() {
        return currentStock;
    }
    
    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }
    
    public Integer getRecommendedQuantity() {
        return recommendedQuantity;
    }
    
    public void setRecommendedQuantity(Integer recommendedQuantity) {
        this.recommendedQuantity = recommendedQuantity;
    }
    
    public Integer getSuggestedLeadTimeDays() {
        return suggestedLeadTimeDays;
    }
    
    public void setSuggestedLeadTimeDays(Integer suggestedLeadTimeDays) {
        this.suggestedLeadTimeDays = suggestedLeadTimeDays;
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
        return "ReorderSuggestion{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", currentStock=" + currentStock +
                ", recommendedQuantity=" + recommendedQuantity +
                ", suggestedLeadTimeDays=" + suggestedLeadTimeDays +
                ", confidence=" + confidence +
                ", reasoning='" + reasoning + '\'' +
                ", status=" + status +
                ", triggerReason=" + triggerReason +
                '}';
    }
}