package com.saas.inventory.dto.request.LostFixedAsset;



import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LostItemDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    // unique identifier for the item
    private String tagNo;


    @NotBlank(message = "Duration is required")
    private String duration;

    @NotBlank(message = "Description is required")
    private String description;

    private String remark; // Optional
}
