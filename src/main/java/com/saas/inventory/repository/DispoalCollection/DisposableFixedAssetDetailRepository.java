package com.saas.inventory.repository.DispoalCollection;


import com.saas.inventory.model.DisposalCollection.DisposableFixedAssetDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DisposableFixedAssetDetailRepository extends JpaRepository<DisposableFixedAssetDetail, UUID> {
}
