package com.saas.inventory.dto.response.FixedAssetDisposal;

import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetDisposalDetailResponse {
    private UUID itemId;
    private String tagNumber;

    private String itemLocation;
    private String disposalMethod;

}
