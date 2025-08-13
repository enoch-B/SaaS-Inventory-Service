package com.saas.inventory.dto.response.FixedAssetTransfer;

import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.TransferType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FixedAssetTransferResponse extends BaseResponse {
    private UUID departmentId;
    private UUID transferToId;
    private UUID transferFromId;
    private String transferNo;

    private TransferType transferType; // Enum for transfer type (e.g., INTERNAL, EXTERNAL, etc.)


    private List<FixedAssetTransferDetailResponse> transferDetails;
}
