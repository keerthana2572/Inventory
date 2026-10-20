package com.inventory.ims.strategy;

import org.springframework.stereotype.Service;

@Service
public class StrategyConfigurationService {
    private volatile String activePricingStrategy = "ruleBased";
    private volatile String activeReorderStrategy = "ruleBased";
    
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