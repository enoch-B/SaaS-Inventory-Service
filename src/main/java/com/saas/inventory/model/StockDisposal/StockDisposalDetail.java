package com.saas.inventory.model.StockDisposal;


import com.saas.inventory.enums.DisposalMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "stock_disposal_detail")
public class StockDisposalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID itemId; // from item-service


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DisposalMethod disposalMethod; // e.g., "Sell", "Donate", "Recycle", etc.

    @Column(nullable = false)
    private  String description; // Description of the disposal action

    @Column(nullable = false)
    private BigDecimal sellingPrice; // Price at which the item was disposed of, if applicable


    @Column(nullable = false)
    private LocalDate expirationDate; // Date when the item was disposed of



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_disposal_id", nullable = false)
    private StockDisposal StockDisposal; // Reference to the parent StockDisposal entity


}
