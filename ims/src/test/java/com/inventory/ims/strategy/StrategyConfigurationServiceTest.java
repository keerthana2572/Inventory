package com.inventory.ims.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrategyConfigurationServiceTest {

    private StrategyConfigurationService strategyConfigurationService;

    @BeforeEach
    void setUp() {
        strategyConfigurationService = new StrategyConfigurationService();
    }

    @Test
    void testGetActivePricingStrategy_DefaultValue() {
        // Act
        String strategy = strategyConfigurationService.getActivePricingStrategy();
        
        // Assert
        assertEquals("ruleBased", strategy);
    }

    @Test
    void testGetActiveReorderStrategy_DefaultValue() {
        // Act
        String strategy = strategyConfigurationService.getActiveReorderStrategy();
        
        // Assert
        assertEquals("ruleBased", strategy);
    }

    @Test
    void testSetActivePricingStrategy_ChangesValue() {
        // Arrange
        String newStrategy = "newStrategy";
        
        // Act
        strategyConfigurationService.setActivePricingStrategy(newStrategy);
        String strategy = strategyConfigurationService.getActivePricingStrategy();
        
        // Assert
        assertEquals(newStrategy, strategy);
    }

    @Test
    void testSetActiveReorderStrategy_ChangesValue() {
        // Arrange
        String newStrategy = "newStrategy";
        
        // Act
        strategyConfigurationService.setActiveReorderStrategy(newStrategy);
        String strategy = strategyConfigurationService.getActiveReorderStrategy();
        
        // Assert
        assertEquals(newStrategy, strategy);
    }
}