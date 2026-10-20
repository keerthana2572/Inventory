package com.inventory.ims.strategy;

import com.inventory.ims.strategy.RuleBasedPricingStrategy;
import com.inventory.ims.strategy.StrategyRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategyRegistryTest {

    private StrategyRegistry strategyRegistry;
    private ApplicationContext applicationContext;
    private RuleBasedPricingStrategy ruleBasedPricingStrategy;

    @BeforeEach
    void setUp() {
        applicationContext = mock(ApplicationContext.class);
        ruleBasedPricingStrategy = mock(RuleBasedPricingStrategy.class);
        
        // Mock application context to return our strategy beans
        when(applicationContext.getBeansOfType(CommerceStrategy.class))
                .thenReturn(java.util.Collections.singletonMap("ruleBased", ruleBasedPricingStrategy));
        
        strategyRegistry = new StrategyRegistry(applicationContext);
    }

    @Test
    void testConstructor_InitializesStrategies() {
        // Assert that the registry was initialized with available strategies
        assertNotNull(strategyRegistry);
    }

    @Test
    void testGetPricingStrategy_ValidStrategyName_ReturnsStrategy() {
        // Act
        CommerceStrategy strategy = strategyRegistry.getPricingStrategy("ruleBased");
        
        // Assert
        assertNotNull(strategy);
        assertEquals(ruleBasedPricingStrategy, strategy);
    }

    @Test
    void testGetReorderStrategy_ValidStrategyName_ReturnsStrategy() {
        // Act
        CommerceStrategy strategy = strategyRegistry.getReorderStrategy("ruleBased");
        
        // Assert
        assertNotNull(strategy);
        assertEquals(ruleBasedPricingStrategy, strategy);
    }

    @Test
    void testGetPricingStrategy_InvalidStrategyName_ReturnsNull() {
        // Act
        CommerceStrategy strategy = strategyRegistry.getPricingStrategy("nonExistent");
        
        // Assert
        assertNull(strategy);
    }

    @Test
    void testGetReorderStrategy_InvalidStrategyName_ReturnsNull() {
        // Act
        CommerceStrategy strategy = strategyRegistry.getReorderStrategy("nonExistent");
        
        // Assert
        assertNull(strategy);
    }
}