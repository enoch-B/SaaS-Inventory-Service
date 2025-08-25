package com.saas.inventory.dto.request.LostStockItem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class LostStockItemRequest {


    @NotBlank(message = "Lost Stock Item Number is required")
    private String lostStockItemNo;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Registration Date is required")
    private LocalDate registrationDate;

    @NotNull(message = "Region ID is required")
    private String region;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

    @NotNull(message = "Store ID is required")
    private UUID storeId;

    @NotNull(message = "Committee ID is required")
    private UUID committeeId;

    @NotNull(message = "Committee Members ID list must not be null")
    @Size(min = 1, message = "At least one committee member ID is required")
    private List<@NotNull(message = "Committee member ID cannot be null") UUID> committeeMembersId;


    @NotNull(message = "Lost stock item details must not be null")
    @Size(min = 1, message = "At least one lost stock item detail is required")
    @Valid
    private List<LostStockItemDetailRequest> lostStockItemDetailRequest;
}

