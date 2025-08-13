package com.saas.inventory.repository.FixedAssetDisposal;


import com.saas.inventory.model.FixedAssetDisposal.FixedAssetDisposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FixedAssetDisposalRepository extends JpaRepository<FixedAssetDisposal, UUID> {
    @Query("SELECT t.fixedAssetDisposalNo FROM FixedAssetDisposal t " +
            "WHERE t.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM t.createdAt) = :year " +
            "ORDER BY t.createdAt DESC")
    List<String> findRecentDisposalNumbers(UUID tenantId, @Param("year")int currentYear);


    Optional<FixedAssetDisposal> findByTenantIdAndFixedAssetDisposalNo(UUID tenantId, String disposalNumber);

    Page<FixedAssetDisposal> findByTenantId(UUID tenantId, Pageable pageable);

}