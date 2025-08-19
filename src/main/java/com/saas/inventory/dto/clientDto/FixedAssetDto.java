package com.saas.inventory.dto.clientDto;

import com.saas.inventory.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FixedAssetDto extends BaseResponse {
     private UUID id;
     private UUID itemId;
     private UUID departmentId;
     private String farNo;
     private String deliveryStatus;
     private String company;
     private UUID sivId;
     private String systemNumber;
     private Integer warrantyMonths;
     private String warranty;
     private String accountNumber;
     private String description;
     private UUID srId;
     private BigDecimal revaluationCost;
     private String itemType;
     private String tagNumber;
}
