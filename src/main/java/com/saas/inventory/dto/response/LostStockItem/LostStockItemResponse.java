package com.saas.inventory.dto.response.LostStockItem;

import com.saas.inventory.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class LostStockItemResponse extends BaseResponse {
    private UUID id;

    private UUID tenantId;

    private String lostStockItemNo;

    private String status;

    private LocalDate registrationDate;

    private UUID regionId;
    private UUID departmentId;
    private UUID storeId;

    private UUID committeeId; // FK to committee-service
    private List<UUID> committeeMembersId;

    private String fileName;
    private String fileType;
    private byte[] FileBytes;


    private List<LostStockItemDetailResponse> lostStockItemDetailResponses;
}
