package com.inventory.ims.strategy;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StrategyRegistry {
    private final Map<String, CommerceStrategy> pricingStrategies;
    private final Map<String, CommerceStrategy> reorderStrategies;
    private final ApplicationContext applicationContext;
    
    @Autowired
    public StrategyRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.pricingStrategies = new HashMap<>();
        this.reorderStrategies = new HashMap<>();
    }
    
    @PostConstruct
    public void initializeStrategies() {
        // Autodetect all CommerceStrategy beans
        Map<String, CommerceStrategy> strategyBeans = applicationContext.getBeansOfType(CommerceStrategy.class);
        
        for (Map.Entry<String, CommerceStrategy> entry : strategyBeans.entrySet()) {
            String beanName = entry.getKey();
            CommerceStrategy strategy = entry.getValue();
            
            // Register in both maps
            pricingStrategies.put(beanName, strategy);
            reorderStrategies.put(beanName, strategy);
        }
    }
    
    public CommerceStrategy getPricingStrategy(String name) {
        return pricingStrategies.get(name);
    }
    
    public CommerceStrategy getReorderStrategy(String name) {
        return reorderStrategies.get(name);
    }
    
    public Iterable<String> getAvailablePricingStrategies() {
        return pricingStrategies.keySet();
    }
    
    public Iterable<String> getAvailableReorderStrategies() {
        return reorderStrategies.keySet();
    }
}