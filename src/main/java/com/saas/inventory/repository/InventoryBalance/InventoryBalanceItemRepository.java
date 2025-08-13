package com.saas.inventory.repository.InventoryBalance;


import com.saas.inventory.model.InventoryBalance.InventoryBalanceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryBalanceItemRepository extends JpaRepository<InventoryBalanceItem, UUID> {
}
