package com.saas.inventory.dto.request.FixedAssetDisposal;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetDisposalDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @NotBlank(message = "Tag number is required")
    private String tagNumber;

    @NotNull(message = "Gain/Loss Value ID is required")


    @NotBlank(message = "Item location is required")
    private String itemLocation;

    @NotBlank(message = "Disposal method is required")
    private String disposalMethod;

    private String description; // Optional
}
