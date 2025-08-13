package com.saas.inventory.dto.response.LostFixedAsset;


import com.saas.inventory.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class LostFixedAssetResponse extends BaseResponse {

    private String lostItemNo;
    private UUID storeId;
    private UUID departmentId;

    private LocalDate registrationDate;

    private String fileName;
    private String fileType;
    private byte[] fileBytes;

    private List<LostItemDetailResponse> lostFixedAssetDetails;


}
