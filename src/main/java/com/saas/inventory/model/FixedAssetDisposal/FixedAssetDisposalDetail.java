package com.saas.inventory.model.FixedAssetDisposal;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "fixed_asset_disposal_details")
public class FixedAssetDisposalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID itemId; // from asset-service



    @Column(nullable = false)
    private String itemLocation;

    private String description;


    @Column(nullable = false)
    private String disposalMethod; // e.g., "Sale", "Donation", "Scrap"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_asset_disposal_id", nullable = false)
    private FixedAssetDisposal fixedAssetDisposal;

}