package com.saas.inventory.dto.response.InventoryBalance;

import com.saas.inventory.dto.response.BaseResponse;
import com.saas.inventory.enums.StoreType;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class InventoryBalanceResponse extends BaseResponse {

    private UUID inventoryCountId;

    private StoreType storeType;



    private List<InventoryBalanceItemResponse> inventoryBalanceItemResponse;
}
