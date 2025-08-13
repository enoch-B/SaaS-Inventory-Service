package com.saas.inventory.dto.clientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private UUID id;
    private String storeName;
    private String storeMan;
    private String warehouse;
    private String address;
    private String Category;
    private String subCategory;

}
