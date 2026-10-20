package com.inventory.ims.dto;

public class SimulateOrderDTO {
    private Integer quantity;
    
    // Constructors
    public SimulateOrderDTO() {}
    
    public SimulateOrderDTO(Integer quantity) {
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}