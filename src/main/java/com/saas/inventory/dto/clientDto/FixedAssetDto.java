package com.saas.inventory.dto.clientDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class FixedAssetDto {
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
