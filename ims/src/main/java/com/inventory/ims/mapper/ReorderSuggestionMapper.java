package com.inventory.ims.mapper;

import com.inventory.ims.dto.ReorderSuggestionDTO;
import com.inventory.ims.model.ReorderSuggestion;
import org.springframework.stereotype.Component;

@Component
public class ReorderSuggestionMapper {
    
    public ReorderSuggestionDTO toDTO(ReorderSuggestion reorderSuggestion) {
        if (reorderSuggestion == null) {
            return null;
        }
        
        return new ReorderSuggestionDTO(
            reorderSuggestion.getId(),
            reorderSuggestion.getProductId(),
            reorderSuggestion.getCurrentStock(),
            reorderSuggestion.getRecommendedQuantity(),
            reorderSuggestion.getSuggestedLeadTimeDays(),
            reorderSuggestion.getConfidence(),
            reorderSuggestion.getReasoning(),
            reorderSuggestion.getStatus(),
            reorderSuggestion.getTriggerReason()
        );
    }
    
    public ReorderSuggestion toEntity(ReorderSuggestionDTO reorderSuggestionDTO) {
        if (reorderSuggestionDTO == null) {
            return null;
        }
        
        ReorderSuggestion reorderSuggestion = new ReorderSuggestion();
        reorderSuggestion.setId(reorderSuggestionDTO.getId());
        reorderSuggestion.setProductId(reorderSuggestionDTO.getProductId());
        reorderSuggestion.setCurrentStock(reorderSuggestionDTO.getCurrentStock());
        reorderSuggestion.setRecommendedQuantity(reorderSuggestionDTO.getRecommendedQuantity());
        reorderSuggestion.setSuggestedLeadTimeDays(reorderSuggestionDTO.getSuggestedLeadTimeDays());
        reorderSuggestion.setConfidence(reorderSuggestionDTO.getConfidence());
        reorderSuggestion.setReasoning(reorderSuggestionDTO.getReasoning());
        reorderSuggestion.setStatus(reorderSuggestionDTO.getStatus());
        reorderSuggestion.setTriggerReason(reorderSuggestionDTO.getTriggerReason());
        
        return reorderSuggestion;
    }
}