package com.inventory.ims.service;

import com.inventory.ims.model.*;
import com.inventory.ims.repository.PricingSuggestionRepository;
import com.inventory.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PricingSuggestionService {
    
    private final PricingSuggestionRepository pricingSuggestionRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public PricingSuggestionService(PricingSuggestionRepository pricingSuggestionRepository,
                                   ProductRepository productRepository) {
        this.pricingSuggestionRepository = pricingSuggestionRepository;
        this.productRepository = productRepository;
    }
    
    public List<PricingSuggestion> getAllPricingSuggestions() {
        return pricingSuggestionRepository.findAll();
    }
    
    public List<PricingSuggestion> getPricingSuggestionsByStatus(SuggestionStatus status) {
        return pricingSuggestionRepository.findByStatus(status);
    }
    
    public List<PricingSuggestion> getPricingSuggestionsByProduct(String productId) {
        return pricingSuggestionRepository.findByProductId(productId);
    }
    
    public Optional<PricingSuggestion> getPricingSuggestionById(String id) {
        return pricingSuggestionRepository.findById(id);
    }
    
    @Transactional
    public PricingSuggestion updatePricingSuggestionStatus(String suggestionId, SuggestionStatus newStatus) {
        Optional<PricingSuggestion> suggestionOpt = pricingSuggestionRepository.findById(suggestionId);
        if (!suggestionOpt.isPresent()) {
            throw new RuntimeException("Pricing suggestion not found with id: " + suggestionId);
        }
        
        PricingSuggestion suggestion = suggestionOpt.get();
        SuggestionStatus oldStatus = suggestion.getStatus();
        suggestion.setStatus(newStatus);
        suggestion.setUpdatedAt(LocalDateTime.now());
        
        PricingSuggestion updatedSuggestion = pricingSuggestionRepository.save(suggestion);
        
        // If accepted, update the product price
        if (newStatus == SuggestionStatus.ACCEPTED && oldStatus != SuggestionStatus.ACCEPTED) {
            Optional<Product> productOpt = productRepository.findById(suggestion.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                product.setCurrentPrice(suggestion.getRecommendedPrice());
                
                // If product was in PRICE_REVIEW_PENDING status, move it back to ACTIVE
                if (product.getStatus() == ProductStatus.PRICE_REVIEW_PENDING) {
                    product.setStatus(ProductStatus.ACTIVE);
                }
                
                productRepository.save(product);
            }
        }
        
        return updatedSuggestion;
    }
}