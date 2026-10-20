package com.inventory.ims.strategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StrategyConfigurationService {
    @Value("${strategy.pricing.default:ruleBased}")
    private String activePricingStrategy;
    
    @Value("${strategy.reorder.default:ruleBased}")
    private String activeReorderStrategy;
    
    public void setActivePricingStrategy(String strategyName) {
        this.activePricingStrategy = strategyName;
    }
    
    public void setActiveReorderStrategy(String strategyName) {
        this.activeReorderStrategy = strategyName;
    }
    
    public String getActivePricingStrategy() {
        return activePricingStrategy;
    }
    
    public String getActiveReorderStrategy() {
        return activeReorderStrategy;
    }
}