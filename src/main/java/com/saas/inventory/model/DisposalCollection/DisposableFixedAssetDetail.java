package com.saas.inventory.model.DisposalCollection;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Table(name = "disposable_asset_detail")
@Data
public class DisposableFixedAssetDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "disposable_asset_id")
    private DisposableAsset disposableAsset;

    private UUID itemId; // From item-service

    private String description;
    private Integer quantity;
    private LocalDate expirationDate;
    private String batchNo;


}