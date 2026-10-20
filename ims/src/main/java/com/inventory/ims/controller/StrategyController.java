package com.inventory.ims.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/strategies")
public class StrategyController {
    
    private final StrategyConfigurationService configService;
    private final StrategyRegistry strategyRegistry;
    
    @Autowired
    public StrategyController(StrategyConfigurationService configService, StrategyRegistry strategyRegistry) {
        this.configService = configService;
        this.strategyRegistry = strategyRegistry;
    }
    
    @PostMapping("/switch")
    public ResponseEntity<String> switchStrategies(
            @RequestParam String pricingStrategy,
            @RequestParam String reorderStrategy) {
        
        // Validate pricing strategy
        boolean pricingStrategyExists = false;
        for (String availableStrategy : strategyRegistry.getAvailablePricingStrategies()) {
            if (availableStrategy.equals(pricingStrategy)) {
                pricingStrategyExists = true;
                break;
            }
        }
        
        if (!pricingStrategyExists) {
            return ResponseEntity.badRequest().body("Pricing strategy '" + pricingStrategy + "' not found. Available strategies: " + 
                String.join(", ", (Iterable<String>) strategyRegistry.getAvailablePricingStrategies()));
        }
        
        // Validate reorder strategy
        boolean reorderStrategyExists = false;
        for (String availableStrategy : strategyRegistry.getAvailableReorderStrategies()) {
            if (availableStrategy.equals(reorderStrategy)) {
                reorderStrategyExists = true;
                break;
            }
        }
        
        if (!reorderStrategyExists) {
            return ResponseEntity.badRequest().body("Reorder strategy '" + reorderStrategy + "' not found. Available strategies: " + 
                String.join(", ", (Iterable<String>) strategyRegistry.getAvailableReorderStrategies()));
        }
        
        configService.setActivePricingStrategy(pricingStrategy);
        configService.setActiveReorderStrategy(reorderStrategy);
        
        return ResponseEntity.ok("Strategies switched successfully. Pricing: " + pricingStrategy + ", Reorder: " + reorderStrategy);
    }
    
    @GetMapping("/available")
    public ResponseEntity<Map<String, Iterable<String>>> getAvailableStrategies() {
        Map<String, Iterable<String>> strategies = new HashMap<>();
        strategies.put("pricing", strategyRegistry.getAvailablePricingStrategies());
        strategies.put("reorder", strategyRegistry.getAvailableReorderStrategies());
        return ResponseEntity.ok(strategies);
    }
    
    @GetMapping("/active")
    public ResponseEntity<Map<String, String>> getActiveStrategies() {
        Map<String, String> activeStrategies = new HashMap<>();
        activeStrategies.put("pricing", configService.getActivePricingStrategy());
        activeStrategies.put("reorder", configService.getActiveReorderStrategy());
        return ResponseEntity.ok(activeStrategies);
    }
}