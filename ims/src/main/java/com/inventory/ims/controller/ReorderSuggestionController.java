package com.inventory.ims.controller;

import com.inventory.ims.dto.UpdateSuggestionDTO;
import com.inventory.ims.model.ReorderSuggestion;
import com.inventory.ims.service.ReorderSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reorder-suggestions")
@CrossOrigin(origins = "http://localhost:5173")
public class ReorderSuggestionController {
    
    private final ReorderSuggestionService reorderSuggestionService;
    
    @Autowired
    public ReorderSuggestionController(ReorderSuggestionService reorderSuggestionService) {
        this.reorderSuggestionService = reorderSuggestionService;
    }
    
    // PATCH /reorder-suggestions/{id} - accept/reject; accept updates stock (simulated inbound shipment)
    @PatchMapping("/{id}")
    public ResponseEntity<ReorderSuggestion> updateReorderSuggestion(
            @PathVariable String id,
            @RequestBody UpdateSuggestionDTO updateSuggestionDTO) {
        ReorderSuggestion updatedSuggestion = reorderSuggestionService.updateSuggestionStatus(id, updateSuggestionDTO.getStatus());
        return ResponseEntity.ok(updatedSuggestion);
    }
}