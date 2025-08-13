package com.saas.inventory.dto.request.LostStockItem;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class LostStockItemDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String description;

    @NotBlank(message = "Duration is required")
    private String duration;

    private String remark; // optional
}
