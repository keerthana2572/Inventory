package com.inventory.ims.service;

import com.inventory.ims.model.*;
import com.inventory.ims.repository.ReorderSuggestionRepository;
import com.inventory.ims.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReorderSuggestionService {
    
    private final ReorderSuggestionRepository reorderSuggestionRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public ReorderSuggestionService(ReorderSuggestionRepository reorderSuggestionRepository,
                                   ProductRepository productRepository) {
        this.reorderSuggestionRepository = reorderSuggestionRepository;
        this.productRepository = productRepository;
    }
    
    public List<ReorderSuggestion> getAllReorderSuggestions() {
        return reorderSuggestionRepository.findAll();
    }
    
    public List<ReorderSuggestion> getReorderSuggestionsByStatus(SuggestionStatus status) {
        return reorderSuggestionRepository.findByStatus(status);
    }
    
    public List<ReorderSuggestion> getReorderSuggestionsByProduct(String productId) {
        return reorderSuggestionRepository.findByProductId(productId);
    }
    
    public Optional<ReorderSuggestion> getReorderSuggestionById(String id) {
        return reorderSuggestionRepository.findById(id);
    }
    
    @Transactional
    public ReorderSuggestion updateReorderSuggestionStatus(String suggestionId, SuggestionStatus newStatus) {
        Optional<ReorderSuggestion> suggestionOpt = reorderSuggestionRepository.findById(suggestionId);
        if (!suggestionOpt.isPresent()) {
            throw new RuntimeException("Reorder suggestion not found with id: " + suggestionId);
        }
        
        ReorderSuggestion suggestion = suggestionOpt.get();
        SuggestionStatus oldStatus = suggestion.getStatus();
        suggestion.setStatus(newStatus);
        suggestion.setUpdatedAt(LocalDateTime.now());
        
        ReorderSuggestion updatedSuggestion = reorderSuggestionRepository.save(suggestion);
        
        // If accepted, update the product stock (simulated inbound shipment)
        if (newStatus == SuggestionStatus.ACCEPTED && oldStatus != SuggestionStatus.ACCEPTED) {
            Optional<Product> productOpt = productRepository.findById(suggestion.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                // Simulate inbound shipment by increasing stock
                Integer newStock = product.getStockLevel() + suggestion.getRecommendedQuantity();
                product.setStockLevel(newStock);
                
                // If product was OUT_OF_STOCK, update status to ACTIVE
                if (product.getStatus() == ProductStatus.OUT_OF_STOCK && newStock > 0) {
                    product.setStatus(ProductStatus.ACTIVE);
                }
                
                productRepository.save(product);
            }
        }
        
        return updatedSuggestion;
    }
}