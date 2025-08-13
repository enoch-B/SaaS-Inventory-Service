package com.saas.inventory.dto.request.FixedAssetReturn;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class FixedAssetReturnRequest {
    @NotNull
    private String assetReturnNo;

    @NotNull(message = "Department ID is required")
    private UUID departmentId;

    @NotNull(message = "Store ID is required")
    private UUID storeId;

    @NotNull(message = "Returned By name is required")
    private UUID returnedById;

    @NotNull(message = "Status is required")
    private String returnStatus;

    @NotNull(message = "Received date is required")
    private LocalDate receivedDate;

    @NotNull(message = "Returned date is required")
    private LocalDate returnedDate;

    @NotNull(message = "Return details must not be null")
    @Size(min = 1, message = "At least one return detail is required")
    @Valid
    private List<FixedAssetReturnDetailRequest> returnDetailRequests;

}
