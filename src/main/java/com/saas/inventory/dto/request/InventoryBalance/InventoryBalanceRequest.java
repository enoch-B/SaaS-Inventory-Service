package com.saas.inventory.dto.request.InventoryBalance;


import com.saas.inventory.enums.StoreType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryBalanceRequest {

    @NotNull(message = "Inventory Count ID is required")
    private UUID inventoryCountId;

    @NotNull(message = "Store Type is required")
    private StoreType storeType;

    @NotNull(message = "Inventory balance items must not be null")
    @Size(min = 1, message = "At least one inventory balance item is required")
    @Valid
    private List<InventoryBalanceItemRequest> inventoryBalanceItemRequest;
}
