package com.saas.inventory.dto.response.DiposalCollection;


import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.DisposableType;
import com.saas.inventory.enums.DisposalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DisposableAssetResponse extends BaseResponse {

    private String drNo;
    private UUID storeId;
    private UUID departmentId;

    private DisposableType disposableType;
    private LocalDate requisitionDate;
    private DisposalStatus disposalStatus;


    private List<DisposableFixedAssetDetailResponse> disposableFixedAssetDetails;

}
