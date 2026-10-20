package com.inventory.ims.dto;

import com.inventory.ims.model.SuggestionStatus;

public class UpdateSuggestionDTO {
    private SuggestionStatus status;
    
    // Constructors
    public UpdateSuggestionDTO() {}
    
    public UpdateSuggestionDTO(SuggestionStatus status) {
        this.status = status;
    }
    
    // Getters and Setters
    public SuggestionStatus getStatus() {
        return status;
    }
    
    public void setStatus(SuggestionStatus status) {
        this.status = status;
    }
}