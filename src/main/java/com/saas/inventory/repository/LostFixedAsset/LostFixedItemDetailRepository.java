package com.saas.inventory.repository.LostFixedAsset;

import com.saas.inventory.model.LostFixedAsset.LostItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LostFixedItemDetailRepository extends JpaRepository<LostItemDetail, UUID> {
    // Additional query methods can be defined here if needed
}