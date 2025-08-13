package com.saas.inventory.dto.response.FixedAssetReturn;

import com.saas.inventory.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FixedAssetReturnResponse extends BaseResponse {

    private UUID departmentId;
    private UUID storeId;
    private UUID returnedById;
    private String returnStatus;
    private LocalDate receivedDate;
    private LocalDate returnedDate;
    private List<FixedAssetReturnDetailResponse> returnDetails;
}
