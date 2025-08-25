package com.saas.inventory.dto.request.StockDisposal;

import com.saas.inventory.enums.DisposalStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class StockDisposalRequest {


    @NotNull(message = "Store ID is required")
    private UUID storeId;


    @NotNull(message = "Disposal number is required")
    private String disposalNo;

    @NotNull(message = "Disposal status is required")
    @Enumerated(EnumType.STRING)
    private DisposalStatus disposalStatus;

    @NotNull(message = "Propose date is required")
    private LocalDate proposeDate;

    @NotNull(message = "Approved date is required")
    private LocalDate approvedDate;


    @NotNull(message = "Stock disposal details must not be null")
    @Size(min = 1, message = "At least one stock disposal detail is required")
    @Valid
    private List<StockDisposalDetailRequest> stockDisposalDetails;
}

