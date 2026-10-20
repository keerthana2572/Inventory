package com.inventory.ims.dto;

public class UpdateStockDTO {
    private Integer stockLevel;
    
    // Constructors
    public UpdateStockDTO() {}
    
    public UpdateStockDTO(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
    
    // Getters and Setters
    public Integer getStockLevel() {
        return stockLevel;
    }
    
    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }
}