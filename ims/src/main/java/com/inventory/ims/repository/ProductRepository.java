package com.inventory.ims.repository;

import com.inventory.ims.model.Product;
import com.inventory.ims.model.ProductStatus;
import com.inventory.ims.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    
    Optional<Product> findBySku(String sku);
    
    List<Product> findByStatus(ProductStatus status);
    
    List<Product> findByCategory(Category category);
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:category IS NULL OR p.category = :category)")
    List<Product> findByStatusAndCategory(
        @Param("status") ProductStatus status,
        @Param("category") Category category
    );
}