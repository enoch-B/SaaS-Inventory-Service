package com.saas.inventory.dto.response.StockDisposal;

import com.saas.inventory.enums.DisposalMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class StockDisposalDetailResponse {
    private UUID itemId;
    private DisposalMethod disposalMethod;
    private String description;
    private BigDecimal sellingPrice;
    private LocalDate expirationDate;
}
