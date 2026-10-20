package com.inventory.ims.mapper;

import com.inventory.ims.dto.ProductDTO;
import com.inventory.ims.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        
        return new ProductDTO(
            product.getId(),
            product.getSku(),
            product.getName(),
            product.getCategory(),
            product.getCurrentPrice(),
            product.getStockLevel(),
            product.getReorderThreshold(),
            product.getDemandVelocity(),
            product.getStatus()
        );
    }
    
    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setSku(productDTO.getSku());
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setCurrentPrice(productDTO.getCurrentPrice());
        product.setStockLevel(productDTO.getStockLevel());
        product.setReorderThreshold(productDTO.getReorderThreshold());
        product.setDemandVelocity(productDTO.getDemandVelocity());
        product.setStatus(productDTO.getStatus());
        
        return product;
    }
}