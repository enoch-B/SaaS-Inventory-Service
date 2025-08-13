package com.saas.inventory.dto.response.InventoryCount;


import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.CountType;
import com.saas.inventory.enums.StoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCountResponse extends BaseResponse {

    private String inventoryCountNumber;
    private UUID storeId;
    private UUID committeeId;
    private List<UUID> committeeMembersId;
    private CountType countType;
    private StoreType storeType;
    private UUID budgetYearId;
    private LocalDate countDate;

    private List<InventoryCountDetailResponse> inventoryDetails;
}
