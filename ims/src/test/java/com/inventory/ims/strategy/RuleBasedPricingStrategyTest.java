package com.inventory.ims.strategy;

import com.inventory.ims.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class RuleBasedPricingStrategyTest {

    private RuleBasedPricingStrategy strategy;
    
    private Product testProduct;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new RuleBasedPricingStrategy();
        
        // Create a test product
        testProduct = new Product();
        testProduct.setId("TEST-001");
        testProduct.setSku("TEST-SKU-001");
        testProduct.setName("Test Product");
        testProduct.setCategory(Category.ELECTRONICS);
        testProduct.setCurrentPrice(new BigDecimal("29.99"));
        testProduct.setStockLevel(15);
        testProduct.setReorderThreshold(10);
        testProduct.setDemandVelocity(5);
        testProduct.setStatus(ProductStatus.ACTIVE);
    }
    
    @Test
    void testGenerateRecommendations_InventoryLow_IncreasePricing() {
        // Arrange
        testProduct.setStockLevel(5); // Below reorder threshold of 10
        testProduct.setReorderThreshold(10);
        
        // Act
        CommerceRecommendation recommendation = strategy.generateRecommendations(testProduct, TriggerReason.INVENTORY_LOW);
        
        // Assert
        assertNotNull(recommendation);
        assertNotNull(recommendation.getPricing());
        assertNotNull(recommendation.getReorder());
        assertEquals(TriggerReason.INVENTORY_LOW, recommendation.getTriggerReason());
        
        // Pricing assertions
        PricingRecommendation pricing = recommendation.getPricing();
        assertEquals(PriceChangeDirection.INCREASE, pricing.getDirection());
        assertEquals(new BigDecimal("32.99"), pricing.getRecommendedPrice().setScale(2, BigDecimal.ROUND_HALF_UP)); // 29.99 * 1.10
        assertEquals(1.0, pricing.getConfidence());
        assertTrue(pricing.getReasoning().contains("below reorder threshold"));
        assertTrue(pricing.getReasoning().contains("increase price"));
        
        // Reorder assertions
        ReorderRecommendation reorder = recommendation.getReorder();
        assertEquals(25, reorder.getRecommendedQuantity()); // (10 * 3) - 5 = 25
        assertNull(reorder.getSuggestedLeadTimeDays()); // Lead time is optional
        assertEquals(1.0, reorder.getConfidence());
        assertTrue(reorder.getReasoning().contains("below reorder threshold"));
    }
}