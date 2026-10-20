package com.inventory.ims.controller;

import com.inventory.ims.dto.ReorderSuggestionDTO;
import com.inventory.ims.dto.UpdateSuggestionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reorder-suggestions")
@CrossOrigin(origins = "http://localhost:5173")
public class ReorderSuggestionController {
    
    // PATCH /reorder-suggestions/{id} - accept/reject; accept updates stock (simulated inbound shipment)
    @PatchMapping("/{id}")
    public ResponseEntity<ReorderSuggestionDTO> updateReorderSuggestion(
            @PathVariable String id,
            @RequestBody UpdateSuggestionDTO updateSuggestionDTO) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
}