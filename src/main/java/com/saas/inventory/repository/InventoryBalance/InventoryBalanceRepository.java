package com.saas.inventory.repository.InventoryBalance;


import com.saas.inventory.model.InventoryBalance.InventoryBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InventoryBalanceRepository extends JpaRepository<InventoryBalance, UUID> {
    Page<InventoryBalance> findByTenantId(UUID tenantId, Pageable pageable);

    Optional<InventoryBalance> deleteByInventoryCountIdAndTenantId(@Param("inventory_count_id")UUID id, @Param("tenantId") UUID tenantId);
}
