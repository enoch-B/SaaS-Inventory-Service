package com.saas.inventory.dto.response.NeedAssessment;


import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.PurchaseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class NeedAssessmentResponse extends BaseResponse {

    private PurchaseType purchaseType;
    private UUID departmentId;
    private UUID storeId;
    private UUID budgetYearId;

    private List<NeedAssessmentDetailResponse> assessmentResponse;
}
