package com.inventory.ims.strategy;

import com.inventory.ims.model.PricingSuggestion;
import com.inventory.ims.model.ReorderSuggestion;
import com.inventory.ims.model.Product;
import com.inventory.ims.model.TriggerReason;

public interface CommerceStrategy {
    
    PricingSuggestion generatePricingSuggestion(Product product, TriggerReason triggerReason);
    
    ReorderSuggestion generateReorderSuggestion(Product product, TriggerReason triggerReason);
}