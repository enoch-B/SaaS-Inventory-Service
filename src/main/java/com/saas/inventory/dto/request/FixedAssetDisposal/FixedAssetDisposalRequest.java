package com.saas.inventory.dto.request.FixedAssetDisposal;

import com.saas.inventory.enums.DisposalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class FixedAssetDisposalRequest {


    @NotNull(message = "Store ID is required")
    private UUID storeId;


    @NotNull(message = "Fixed Asset Disposal No is required")
    private String fixedAssetDisposalNo;


    @NotNull(message = "Approved date is required")
    private LocalDate approvedDate;


    private DisposalStatus disposalStatus;


    @NotNull(message = "Proposed date is required")
    private LocalDate proposedDate;


    private UUID disposableAssetId;

    @NotNull(message = "Disposal details must not be null")
    @Size(min = 1, message = "At least one disposal detail is required")
    @Valid
    private List<FixedAssetDisposalDetailRequest> disposalDetails;
}
