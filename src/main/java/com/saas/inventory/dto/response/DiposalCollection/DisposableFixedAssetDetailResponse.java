package com.saas.inventory.dto.response.DiposalCollection;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class DisposableFixedAssetDetailResponse {
    private UUID itemId;
//    private String tagNumber; // Unique identifier for the asset

    private String description;
    private Integer quantity;
    private LocalDate expirationDate;
    private String batchNo;
}
