package com.saas.inventory.repository.LostStockItem;

import com.saas.inventory.model.LostStockItem.LostStockItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LostStockItemDetailRepository extends JpaRepository<LostStockItemDetail , UUID> {
}
