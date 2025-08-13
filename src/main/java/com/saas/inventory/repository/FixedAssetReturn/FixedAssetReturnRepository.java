package com.saas.inventory.repository.FixedAssetReturn;


import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FixedAssetReturnRepository extends JpaRepository<FixedAssetReturn, UUID> {
    @Query("SELECT r.assetReturnNo FROM FixedAssetReturn r " +
            "WHERE r.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM r.createdAt) = :year " +
            "ORDER BY r.createdAt DESC")
    List<String> findRecentReturnNumbers(@Param("tenantId") UUID tenantId, @Param("year") int currentYear);

    Page<FixedAssetReturn> findByTenantId(UUID tenantId, Pageable pageable);


}
