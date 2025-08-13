package com.saas.inventory.repository.FixedAssetReturn;


import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturnDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FixedAssetReturnDetailRepository extends JpaRepository<FixedAssetReturnDetail, UUID> {

}
