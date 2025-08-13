package com.saas.inventory.dto.response.NeedAssessment;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class NeedAssessmentDetailResponse {
    private UUID itemId;
    private UUID generalLedger;
    private BigDecimal budgetAmount;
}
