package com.inventory.ims.strategy;

import com.inventory.ims.model.PriceChangeDirection;
import com.inventory.ims.model.TriggerReason;
import java.math.BigDecimal;

public class CommerceRecommendation {
    private PricingRecommendation pricing;
    private ReorderRecommendation reorder;
    private TriggerReason triggerReason;

    public CommerceRecommendation() {}

    public CommerceRecommendation(PricingRecommendation pricing, ReorderRecommendation reorder, TriggerReason triggerReason) {
        this.pricing = pricing;
        this.reorder = reorder;
        this.triggerReason = triggerReason;
    }

    // Getters and Setters
    public PricingRecommendation getPricing() {
        return pricing;
    }

    public void setPricing(PricingRecommendation pricing) {
        this.pricing = pricing;
    }

    public ReorderRecommendation getReorder() {
        return reorder;
    }

    public void setReorder(ReorderRecommendation reorder) {
        this.reorder = reorder;
    }

    public TriggerReason getTriggerReason() {
        return triggerReason;
    }

    public void setTriggerReason(TriggerReason triggerReason) {
        this.triggerReason = triggerReason;
    }
}