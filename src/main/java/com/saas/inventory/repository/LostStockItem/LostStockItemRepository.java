package com.saas.inventory.repository.LostStockItem;

import com.saas.inventory.model.LostStockItem.LostStockItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LostStockItemRepository extends JpaRepository<LostStockItem, UUID> {
    @Query("SELECT t.lostStockItemNo FROM LostStockItem t " +
            "WHERE t.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM t.createdAt) = :year " +
            "ORDER BY t.createdAt DESC")

    List<String> findRecentLostStockItemNo(UUID tenantId, @Param("year") int currentYear);

    Page<LostStockItem> findByTenantId(UUID tenantId, Pageable pageable);

   Optional<LostStockItem>findByTenantIdAndLostStockItemNo(UUID tenantId, String lostStockItemNo);
}
