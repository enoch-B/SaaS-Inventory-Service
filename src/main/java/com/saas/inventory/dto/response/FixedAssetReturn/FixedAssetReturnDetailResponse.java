package com.saas.inventory.dto.response.FixedAssetReturn;

import com.saas.inventory.enums.ItemStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetReturnDetailResponse {
    private UUID itemId;
    private ItemStatus itemStatus;
    private String tagNumber; // unique identifier for the asset
    private String description;
}
