package com.saas.inventory.dto.response.InventoryBalance;

import lombok.Data;

import java.math.BigDecimal;

import java.util.UUID;

@Data
public class InventoryBalanceItemResponse {

    private UUID itemId;
    private BigDecimal quantity;
    private BigDecimal binBalance;
    private BigDecimal difference;
    private String remark;
}
