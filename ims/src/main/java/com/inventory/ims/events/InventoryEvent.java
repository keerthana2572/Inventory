package com.inventory.ims.events;

import com.inventory.ims.model.TriggerReason;

public class InventoryEvent {
    private String productId;
    private TriggerReason triggerReason;
    
    public InventoryEvent(String productId, TriggerReason triggerReason) {
        this.productId = productId;
        this.triggerReason = triggerReason;
    }
    
    // Getters
    public String getProductId() {
        return productId;
    }
    
    public TriggerReason getTriggerReason() {
        return triggerReason;
    }
}