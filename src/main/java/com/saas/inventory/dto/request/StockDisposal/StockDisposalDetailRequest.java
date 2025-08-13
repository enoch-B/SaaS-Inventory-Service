package com.saas.inventory.dto.request.StockDisposal;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class StockDisposalDetailRequest {

    @NotNull(message = "Item ID is required")
    private UUID itemId;

    @NotBlank(message = "Disposal method is required")
    private String disposalMethod;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Selling price must be zero or positive")
    private BigDecimal sellingPrice;

    @NotNull(message = "Expiration date is required")
    private LocalDate expirationDate;
}
