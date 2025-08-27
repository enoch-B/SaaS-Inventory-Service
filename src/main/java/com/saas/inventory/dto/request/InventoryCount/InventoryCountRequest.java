package com.saas.inventory.dto.request.InventoryCount;


import com.saas.inventory.enums.CountType;
import com.saas.inventory.enums.StoreType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Setter
@Getter
public class InventoryCountRequest {

    @NotNull(message = "Store ID is required")
    private UUID storeId;

    @NotNull(message = "Committee ID is required")
    private UUID committeeId;

    @NotBlank(message = "Inventory Count Number is required")
    private String inventoryCountNumber;


    @NotNull(message = "Committee Members ID list must not be null")
    @Size(min = 1, message = "At least one committee member ID is required")
    private List<@NotNull(message = "Committee member ID cannot be null") UUID> committeeMembersId;


    @NotNull(message = "Count Type is required")
    private CountType countType;

    @NotNull(message = "Store Type is required")
    private StoreType storeType;

    @NotNull(message = "Budget Year ID is required")
    private UUID budgetYearId;

    @NotNull(message = "Count Date is required")
    private LocalDate countDate;

     @NotNull(message="inventory count detail must not be null")
    @Size(min = 1, message = "At least one inventory item is required")
    @Valid
    private List<InventoryCountDetailRequest> inventoryItems;
}

