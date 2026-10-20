package com.inventory.ims.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StrategyResolverTest {

    private StrategyResolver strategyResolver;
    
    @Mock
    private StrategyRegistry strategyRegistry;
    
    @Mock
    private StrategyConfigurationService configurationService;
    
    @Mock
    private CommerceStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategyResolver = new StrategyResolver(strategyRegistry, configurationService);
        
        // Set up default mock behavior
        when(configurationService.getActivePricingStrategy()).thenReturn("ruleBased");
        when(configurationService.getActiveReorderStrategy()).thenReturn("ruleBased");
        when(strategyRegistry.getPricingStrategy("ruleBased")).thenReturn(mockStrategy);
        when(strategyRegistry.getReorderStrategy("ruleBased")).thenReturn(mockStrategy);
    }

    @Test
    void testGetActivePricingStrategy_ReturnsCorrectStrategy() {
        // Act
        CommerceStrategy strategy = strategyResolver.getActivePricingStrategy();
        
        // Assert
        assertNotNull(strategy);
        assertEquals(mockStrategy, strategy);
        verify(configurationService).getActivePricingStrategy();
        verify(strategyRegistry).getPricingStrategy("ruleBased");
    }

    @Test
    void testGetActiveReorderStrategy_ReturnsCorrectStrategy() {
        // Act
        CommerceStrategy strategy = strategyResolver.getActiveReorderStrategy();
        
        // Assert
        assertNotNull(strategy);
        assertEquals(mockStrategy, strategy);
        verify(configurationService).getActiveReorderStrategy();
        verify(strategyRegistry).getReorderStrategy("ruleBased");
    }

    @Test
    void testGetActivePricingStrategy_WhenStrategyNotFound_ReturnsNull() {
        // Arrange
        when(strategyRegistry.getPricingStrategy("ruleBased")).thenReturn(null);
        
        // Act
        CommerceStrategy strategy = strategyResolver.getActivePricingStrategy();
        
        // Assert
        assertNull(strategy);
    }

    @Test
    void testGetActiveReorderStrategy_WhenStrategyNotFound_ReturnsNull() {
        // Arrange
        when(strategyRegistry.getReorderStrategy("ruleBased")).thenReturn(null);
        
        // Act
        CommerceStrategy strategy = strategyResolver.getActiveReorderStrategy();
        
        // Assert
        assertNull(strategy);
    }
}