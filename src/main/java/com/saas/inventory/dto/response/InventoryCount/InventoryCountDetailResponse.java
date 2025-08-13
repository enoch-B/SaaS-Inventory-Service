package com.saas.inventory.dto.response.InventoryCount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryCountDetailResponse {
    private UUID itemId;
    private int quantity;
    private String remark;
}
