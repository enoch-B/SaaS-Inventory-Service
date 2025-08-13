package com.saas.inventory.dto.request.NeedAssessment;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class NeedAssessmentDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @NotNull(message = "General ledger ID is required")
    private UUID generalLedger;

    @NotNull(message = "Budget amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Budget amount must be greater than zero")
    private BigDecimal budgetAmount;
}
