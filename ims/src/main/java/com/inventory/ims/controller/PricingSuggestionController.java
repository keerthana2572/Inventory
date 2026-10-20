package com.inventory.ims.controller;

import com.inventory.ims.dto.UpdateSuggestionDTO;
import com.inventory.ims.model.PricingSuggestion;
import com.inventory.ims.service.PricingSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing-suggestions")
@CrossOrigin(origins = "http://localhost:5173")
public class PricingSuggestionController {
    
    private final PricingSuggestionService pricingSuggestionService;
    
    @Autowired
    public PricingSuggestionController(PricingSuggestionService pricingSuggestionService) {
        this.pricingSuggestionService = pricingSuggestionService;
    }
    
    // PATCH /pricing-suggestions/{id} - accept/reject; accept updates Product.currentPrice
    @PatchMapping("/{id}")
    public ResponseEntity<PricingSuggestion> updatePricingSuggestion(
            @PathVariable String id,
            @RequestBody UpdateSuggestionDTO updateSuggestionDTO) {
        PricingSuggestion updatedSuggestion = pricingSuggestionService.updateSuggestionStatus(id, updateSuggestionDTO.getStatus());
        return ResponseEntity.ok(updatedSuggestion);
    }
}