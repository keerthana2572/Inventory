package com.inventory.ims.controller;

import com.inventory.ims.dto.CreateProductDTO;
import com.inventory.ims.dto.ProductDTO;
import com.inventory.ims.dto.SimulateOrderDTO;
import com.inventory.ims.dto.UpdateStockDTO;
import com.inventory.ims.mapper.ProductMapper;
import com.inventory.ims.model.Category;
import com.inventory.ims.model.Product;
import com.inventory.ims.model.ProductStatus;
import com.inventory.ims.service.ProductService;
import com.inventory.ims.strategy.CommerceRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    
    private final ProductService productService;
    private final ProductMapper productMapper;
    
    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }
    
    // POST /products - create product with initial stock and price
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductDTO createProductDTO) {
        Product product = productMapper.toEntity(createProductDTO);
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(productMapper.toDTO(savedProduct));
    }
    
    // GET /products?status=&category= - filterable catalog list
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) Category category) {
        
        List<Product> products;
        if (status != null && category != null) {
            products = productService.getProductsByStatusAndCategory(status, category);
        } else if (status != null) {
            products = productService.getProductsByStatus(status);
        } else if (category != null) {
            products = productService.getProductsByCategory(category);
        } else {
            products = productService.getAllProducts();
        }
        
        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(productDTOs);
    }
    
    // PATCH /products/{id}/stock - update stock level
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable String id,
            @RequestBody UpdateStockDTO updateStockDTO) {
        Product updatedProduct = productService.updateStock(id, updateStockDTO);
        return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
    }
    
    // POST /products/{id}/orders - simulate a sale
    @PostMapping("/{id}/orders")
    public ResponseEntity<ProductDTO> simulateOrder(
            @PathVariable String id,
            @RequestBody SimulateOrderDTO simulateOrderDTO) {
        Product updatedProduct = productService.simulateOrder(id, simulateOrderDTO);
        return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
    }
    
    // POST /products/{id}/suggest-pricing - on-demand pricing suggestion
    @PostMapping("/{id}/suggest-pricing")
    public ResponseEntity<CommerceRecommendation> suggestPricing(@PathVariable String id) {
        CommerceRecommendation recommendation = productService.generatePricingRecommendation(id);
        return ResponseEntity.ok(recommendation);
    }
    
    // POST /products/{id}/suggest-pricing/stream - SSE token stream of AI reasoning
    @PostMapping("/{id}/suggest-pricing/stream")
    public SseEmitter streamPricingSuggestions(@PathVariable String id) {
        // Implementation will be added later
        return new SseEmitter();
    }
    
    // POST /products/{id}/suggest-reorder - on-demand reorder suggestion
    @PostMapping("/{id}/suggest-reorder")
    public ResponseEntity<CommerceRecommendation> suggestReorder(@PathVariable String id) {
        CommerceRecommendation recommendation = productService.generateReorderRecommendation(id);
        return ResponseEntity.ok(recommendation);
    }
}