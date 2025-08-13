package com.saas.inventory.repository.InventoryCount;


import com.saas.inventory.model.InventoryCountSheet.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InventoryCountDetailRepository extends JpaRepository<InventoryDetail, UUID> {
    List<InventoryDetail> findAllByInventoryCountId(UUID inventoryCountId);

    @Modifying
    @Query("DELETE FROM InventoryDetail d WHERE d.inventoryCount.id = :countId")
    void deleteAllByInventoryCountId(@Param("countId") UUID countId);
}