package com.inventory.ims.repository;

import com.inventory.ims.model.PricingSuggestion;
import com.inventory.ims.model.SuggestionStatus;
import com.inventory.ims.model.TriggerReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PricingSuggestionRepository extends JpaRepository<PricingSuggestion, String> {
    
    List<PricingSuggestion> findByProductId(String productId);
    
    List<PricingSuggestion> findByStatus(SuggestionStatus status);
    
    @Query("SELECT ps FROM PricingSuggestion ps WHERE " +
           "ps.productId = :productId AND " +
           "ps.triggerReason = :triggerReason AND " +
           "ps.status = 'PENDING'")
    Optional<PricingSuggestion> findPendingByProductIdAndTriggerReason(
        @Param("productId") String productId,
        @Param("triggerReason") TriggerReason triggerReason
    );
    
    @Query("SELECT ps FROM PricingSuggestion ps WHERE " +
           "ps.productId = :productId AND " +
           "ps.triggerReason = :triggerReason AND " +
           "ps.status = :status")
    List<PricingSuggestion> findByProductIdAndTriggerReasonAndStatus(
        @Param("productId") String productId,
        @Param("triggerReason") TriggerReason triggerReason,
        @Param("status") SuggestionStatus status
    );
    
    // Add the missing method that's used in ProductService
    Optional<PricingSuggestion> findFirstByProductIdAndTriggerReasonAndStatus(
        String productId, 
        TriggerReason triggerReason, 
        SuggestionStatus status
    );
    
    List<PricingSuggestion> findByProductIdAndStatus(String productId, SuggestionStatus status);
}