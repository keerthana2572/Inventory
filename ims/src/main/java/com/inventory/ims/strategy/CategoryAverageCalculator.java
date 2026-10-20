package com.inventory.ims.strategy;

import com.inventory.ims.model.Category;
import com.inventory.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryAverageCalculator {
    
    private final ProductRepository productRepository;
    
    @Autowired
    public CategoryAverageCalculator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public double getCategoryAverage(Category category) {
        // In a real implementation, this would calculate the actual average
        // For now, we'll return a fixed value based on category
        switch (category) {
            case ELECTRONICS:
                return 5.0;
            case APPAREL:
                return 8.0;
            case HOME:
                return 3.0;
            default:
                return 5.0; // Default average
        }
    }
}