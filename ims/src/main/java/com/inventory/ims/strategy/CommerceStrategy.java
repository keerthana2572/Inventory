package com.inventory.ims.strategy;

import com.inventory.ims.model.Product;
import com.inventory.ims.model.TriggerReason;

public interface CommerceStrategy {
    CommerceRecommendation generateRecommendations(Product product, TriggerReason triggerReason);
}