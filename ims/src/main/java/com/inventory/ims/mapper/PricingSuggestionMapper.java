package com.inventory.ims.mapper;

import com.inventory.ims.dto.PricingSuggestionDTO;
import com.inventory.ims.model.PricingSuggestion;
import org.springframework.stereotype.Component;

@Component
public class PricingSuggestionMapper {
    
    public PricingSuggestionDTO toDTO(PricingSuggestion pricingSuggestion) {
        if (pricingSuggestion == null) {
            return null;
        }
        
        return new PricingSuggestionDTO(
            pricingSuggestion.getId(),
            pricingSuggestion.getProductId(),
            pricingSuggestion.getCurrentPrice(),
            pricingSuggestion.getRecommendedPrice(),
            pricingSuggestion.getChangeDirection(),
            pricingSuggestion.getConfidence(),
            pricingSuggestion.getReasoning(),
            pricingSuggestion.getStatus(),
            pricingSuggestion.getTriggerReason()
        );
    }
    
    public PricingSuggestion toEntity(PricingSuggestionDTO pricingSuggestionDTO) {
        if (pricingSuggestionDTO == null) {
            return null;
        }
        
        PricingSuggestion pricingSuggestion = new PricingSuggestion();
        pricingSuggestion.setId(pricingSuggestionDTO.getId());
        pricingSuggestion.setProductId(pricingSuggestionDTO.getProductId());
        pricingSuggestion.setCurrentPrice(pricingSuggestionDTO.getCurrentPrice());
        pricingSuggestion.setRecommendedPrice(pricingSuggestionDTO.getRecommendedPrice());
        pricingSuggestion.setChangeDirection(pricingSuggestionDTO.getChangeDirection());
        pricingSuggestion.setConfidence(pricingSuggestionDTO.getConfidence());
        pricingSuggestion.setReasoning(pricingSuggestionDTO.getReasoning());
        pricingSuggestion.setStatus(pricingSuggestionDTO.getStatus());
        pricingSuggestion.setTriggerReason(pricingSuggestionDTO.getTriggerReason());
        
        return pricingSuggestion;
    }
}