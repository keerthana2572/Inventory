package com.inventory.ims.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategyResolver {
    private final StrategyRegistry strategyRegistry;
    private final StrategyConfigurationService configService;
    
    @Autowired
    public StrategyResolver(StrategyRegistry strategyRegistry, StrategyConfigurationService configService) {
        this.strategyRegistry = strategyRegistry;
        this.configService = configService;
    }
    
    public CommerceStrategy getActivePricingStrategy() {
        String strategyName = configService.getActivePricingStrategy();
        return strategyRegistry.getPricingStrategy(strategyName);
    }
    
    public CommerceStrategy getActiveReorderStrategy() {
        String strategyName = configService.getActiveReorderStrategy();
        return strategyRegistry.getReorderStrategy(strategyName);
    }
}