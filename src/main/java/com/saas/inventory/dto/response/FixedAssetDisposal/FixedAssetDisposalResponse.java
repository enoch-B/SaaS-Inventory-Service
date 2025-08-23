package com.saas.inventory.dto.response.FixedAssetDisposal;


import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.DisposalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Data
public class FixedAssetDisposalResponse extends BaseResponse {

    private UUID storeId; // Reference to Store from store-service

    private String fixedAssetDisposalNo; // Unique identifier for the disposal

    private LocalDate approvedDate; // Date when the disposal was approved
    private UUID disposableAssetId; // Reference to the asset being disposed of

    private LocalDate proposedDate;

    private String fileName;
    private String fileType;
    private byte[] fileData; // Binary data of the attached file

    private DisposalStatus disposalStatus; // Status of the disposal (e.g., PENDING, APPROVED, REJECTED)
    private List<FixedAssetDisposalDetailResponse> disposalDetailResponses; // List of details related to the disposal

}
