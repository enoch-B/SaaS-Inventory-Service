package com.saas.inventory.dto.response.LostFixedAsset;
import lombok.Data;

import java.util.UUID;

@Data
public class LostItemDetailResponse {

    private UUID itemId;

    private String tagNo;    // unique identifier for the item

    private String duration;
    private String description;
    private String remark;

}
