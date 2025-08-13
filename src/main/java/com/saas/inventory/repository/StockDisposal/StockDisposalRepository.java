package com.saas.inventory.repository.StockDisposal;

import com.saas.inventory.model.StockDisposal.StockDisposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockDisposalRepository extends JpaRepository<StockDisposal, UUID> {
    @Query("SELECT s.disposalNo FROM StockDisposal s " +
            "WHERE s.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM s.createdAt) = :year " +
            "ORDER BY s.createdAt DESC")

    List<String> findRecentDisposalNo(UUID tenantId, @Param("year") int currentYear);

    Page<StockDisposal> findByTenantId(UUID tenantId, Pageable pageable);

    Optional<StockDisposal> findByTenantIdAndDisposalNo(UUID tenantId, String disposalNo);
}
