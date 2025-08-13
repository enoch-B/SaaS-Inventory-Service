package com.saas.inventory.repository.LostFixedAsset;

import com.saas.inventory.model.LostFixedAsset.LostFixedAsset;
import jakarta.ws.rs.client.ClientRequestFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LostFixedAssetRepository extends JpaRepository<LostFixedAsset, UUID> {
    @Query("SELECT t.lostItemNo FROM LostFixedAsset t " +
            "WHERE t.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM t.createdAt) = :year " +
            "ORDER BY t.createdAt DESC")
    List<String> findRecentLostItemNo(UUID tenantId, @Param("year") int currentYear);

    Page<LostFixedAsset> findByTenantId(UUID tenantId, Pageable pageable);

    Optional<LostFixedAsset> findByTenantIdAndLostItemNo(UUID tenantId, String lostItemNo);
}