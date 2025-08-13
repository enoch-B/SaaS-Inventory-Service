package com.saas.inventory.dto.request.LostFixedAsset;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LostFixedAssetRequest {


    @NotBlank(message = "Lost item number is required")
    private String lostItemNo;

    @NotNull(message = "Store ID is required")
    private UUID storeId;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;


    @NotNull(message = "Registration date is required")
    private LocalDate registrationDate;

    // File data fields -
    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "File type is required")
    private String fileType;

    @NotNull(message = "File data is required")
    private byte[] data;

    @NotNull(message = "Lost item details must not be null")
    @Size(min = 1, message = "At least one lost item detail is required")
    @Valid
    private List<LostItemDetailRequest> lostItemDetails;
}

