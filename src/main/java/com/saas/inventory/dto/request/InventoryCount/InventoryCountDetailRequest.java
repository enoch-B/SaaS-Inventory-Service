package com.saas.inventory.dto.request.InventoryCount;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@AllArgsConstructor
@Getter
@Setter
public class InventoryCountDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    private String remark;
}

