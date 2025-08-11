package com.saas.inventory.dto.request.DisposableAsset;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class DisposableFixedAssetDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

//    @NotBlank(message = "Tag number is required")
//    private String tagNumber;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private LocalDate expirationDate;

    private String batchNo; // Optional — can be left as-is
}
