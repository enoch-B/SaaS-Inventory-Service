package com.saas.inventory.dto.request.FixedAssetTransfer;

import com.saas.inventory.enums.TransferType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FixedAssetTransferRequest {

    @NotBlank(message = "Transfer number is required")
    private String transferNo;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

    @NotNull(message = "Transfer To ID is required")
    private UUID transferToId;

    @NotNull(message = "Transfer From ID is required")
    private UUID transferFromId;


    @NotNull(message = "Transfer type is required")
    private TransferType transferType;

    @NotNull(message = "Transfer details must not be null")
    @Size(min = 1, message = "At least one transfer detail is required")
    @Valid
    private List<FixedAssetTransferDetailRequest> transferDetails;
}
