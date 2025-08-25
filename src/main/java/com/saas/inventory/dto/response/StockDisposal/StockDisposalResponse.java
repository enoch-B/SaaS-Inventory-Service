package com.saas.inventory.dto.response.StockDisposal;

import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.DisposalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class StockDisposalResponse extends BaseResponse {
    private UUID storeId;
    private String disposalNo;
    private DisposalStatus disposalStatus;
    private LocalDate proposeDate;
    private LocalDate approvedDate;
    private String fileName;
    private String fileType;
    private byte[] fileBytes;

    private List<StockDisposalDetailResponse> stockDisposalDetails;
}
