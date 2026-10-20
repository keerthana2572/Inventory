package com.inventory.ims.repository;

import com.inventory.ims.model.ReorderSuggestion;
import com.inventory.ims.model.SuggestionStatus;
import com.inventory.ims.model.TriggerReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReorderSuggestionRepository extends JpaRepository<ReorderSuggestion, String> {
    
    List<ReorderSuggestion> findByProductId(String productId);
    
    List<ReorderSuggestion> findByStatus(SuggestionStatus status);
    
    @Query("SELECT rs FROM ReorderSuggestion rs WHERE " +
           "rs.productId = :productId AND " +
           "rs.triggerReason = :triggerReason AND " +
           "rs.status = 'PENDING'")
    Optional<ReorderSuggestion> findPendingByProductIdAndTriggerReason(
        @Param("productId") String productId,
        @Param("triggerReason") TriggerReason triggerReason
    );
    
    @Query("SELECT rs FROM ReorderSuggestion rs WHERE " +
           "rs.productId = :productId AND " +
           "rs.triggerReason = :triggerReason AND " +
           "rs.status = :status")
    List<ReorderSuggestion> findByProductIdAndTriggerReasonAndStatus(
        @Param("productId") String productId,
        @Param("triggerReason") TriggerReason triggerReason,
        @Param("status") SuggestionStatus status
    );
    
    // Add the missing method that's used in ProductService
    Optional<ReorderSuggestion> findFirstByProductIdAndTriggerReasonAndStatus(
        String productId, 
        TriggerReason triggerReason, 
        SuggestionStatus status
    );
    
    List<ReorderSuggestion> findByProductIdAndStatus(String productId, SuggestionStatus status);
}