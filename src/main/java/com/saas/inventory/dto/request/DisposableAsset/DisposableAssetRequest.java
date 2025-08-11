package com.saas.inventory.dto.request.DisposableAsset;


import com.saas.inventory.enums.DisposableType;
import com.saas.inventory.enums.DisposalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class DisposableAssetRequest {


    private String drNo;

    @NotNull(message = "Store ID is required")
    private UUID storeId;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

    @NotNull(message = "Disposable type is required")
    private DisposableType disposableType;

    @NotNull(message = "Requisition date is required")
    private LocalDate requisitionDate;

    @NotNull(message = "Disposal status is required")
    private DisposalStatus disposalStatus;


    @NotNull(message = "Disposable asset details must not be null")
    @Size(min = 1, message = "At least one disposable asset detail is required")
    @Valid
    private List<DisposableFixedAssetDetailRequest> disposableFixedAssetDetails;
}

