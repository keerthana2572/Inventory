package com.inventory.ims.controller;

import com.inventory.ims.dto.CreateProductDTO;
import com.inventory.ims.dto.ProductDTO;
import com.inventory.ims.dto.SimulateOrderDTO;
import com.inventory.ims.dto.UpdateStockDTO;
import com.inventory.ims.model.Category;
import com.inventory.ims.model.ProductStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    
    // POST /products - create product with initial stock and price
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
    
    // GET /products?status=&category= - filterable catalog list
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Category category) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
    
    // PATCH /products/{id}/stock - update stock level
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable String id,
            @RequestBody UpdateStockDTO updateStockDTO) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
    
    // POST /products/{id}/orders - simulate a sale
    @PostMapping("/{id}/orders")
    public ResponseEntity<ProductDTO> simulateOrder(
            @PathVariable String id,
            @RequestBody SimulateOrderDTO simulateOrderDTO) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
    
    // POST /products/{id}/suggest-pricing - on-demand pricing suggestion
    @PostMapping("/{id}/suggest-pricing")
    public ResponseEntity<?> suggestPricing(@PathVariable String id) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
    
    // POST /products/{id}/suggest-pricing/stream - SSE token stream of AI reasoning
    @PostMapping("/{id}/suggest-pricing/stream")
    public SseEmitter streamPricingSuggestions(@PathVariable String id) {
        // Implementation will be added later in service layer
        return new SseEmitter();
    }
    
    // POST /products/{id}/suggest-reorder - on-demand reorder suggestion
    @PostMapping("/{id}/suggest-reorder")
    public ResponseEntity<?> suggestReorder(@PathVariable String id) {
        // Implementation will be added later in service layer
        return ResponseEntity.ok().build();
    }
}