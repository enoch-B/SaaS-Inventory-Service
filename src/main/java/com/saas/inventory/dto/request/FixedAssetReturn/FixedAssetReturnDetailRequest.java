package com.saas.inventory.dto.request.FixedAssetReturn;


import com.saas.inventory.enums.ItemStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class FixedAssetReturnDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @NotNull(message = "Item status is required")
    private ItemStatus itemStatus;

    private String description;

}
