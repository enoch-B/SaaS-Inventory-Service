package com.saas.inventory.dto.request.FixedAssetTransfer;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetTransferDetailRequest {
    @NotNull
    private UUID itemId; // Reference to Item from item-service


    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    private String remark;

    @NotNull
    private String description;

}
