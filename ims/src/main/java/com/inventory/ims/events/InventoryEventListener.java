package com.inventory.ims.events;

import com.inventory.ims.model.Product;
import com.inventory.ims.model.TriggerReason;
import com.inventory.ims.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(InventoryEventListener.class);
    
    @Autowired
    private ProductService productService;
    
    @EventListener
    @Async
    public void handleInventoryEvent(InventoryEvent event) {
        logger.info("Processing inventory event for product: {}, trigger: {}", 
                   event.getProductId(), event.getTriggerReason());
        
        try {
            // Generate recommendations based on the trigger
            productService.generateAutomaticRecommendations(event.getProductId(), event.getTriggerReason());
        } catch (Exception e) {
            logger.error("Error processing inventory event for product: {}", event.getProductId(), e);
        }
    }
}