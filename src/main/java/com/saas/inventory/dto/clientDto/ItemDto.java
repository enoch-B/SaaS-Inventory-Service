package com.saas.inventory.dto.clientDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saas.inventory.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto extends BaseResponse {

        private UUID id;
        private String category;
        private String subCategory;
        private String stockMovementType;
        private String itemCode;
        private String itemName;
        private String itemProperty;
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        private String itemCategory;
        private String bachNumber;
        private String serialNumber;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime expireDate;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime purchasedDate;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime serviceDate;

        private String minimumLevel;
        private String maximumLevel;
        private String recordLevel;
        private String safetyStock;

    }

