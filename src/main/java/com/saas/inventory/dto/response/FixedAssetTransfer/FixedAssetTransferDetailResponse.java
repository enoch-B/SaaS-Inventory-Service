package com.saas.inventory.dto.response.FixedAssetTransfer;

import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetTransferDetailResponse {
    private UUID itemId; // Reference to Item from item-service
    private String tagNumber; //unique identifier for the asset
    private Integer quantity;
    private String remark;
    private String description;
}
