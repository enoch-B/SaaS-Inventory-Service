package com.saas.inventory.repository.DispoalCollection;


import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DisposableAssetRepository extends JpaRepository<DisposableAsset, UUID> {
    @Query("SELECT da.drNo FROM DisposableAsset da " +
            "WHERE da.tenantId = :tenantId AND FUNCTION('YEAR', da.createdAt) = :currentYear " +
            "ORDER BY da.createdAt DESC")
    List<String> findRecentFADNumbers(UUID tenantId, @Param("currentYear") int currentYear);

    Page<DisposableAsset> findByTenantId(UUID tenantId, Pageable pageable);

    Optional<DisposableAsset> findByDrNoAndTenantId(String drNo, UUID tenantId);
}
