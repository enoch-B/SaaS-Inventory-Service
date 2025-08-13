package com.saas.inventory.dto.response.LostStockItem;


import lombok.Data;

import java.util.UUID;

@Data
public class LostStockItemDetailResponse {
    private UUID itemId;        // FK to item-service

    private Integer Quantity;

    private String description;
    private String duration;
    private String remark;


}
