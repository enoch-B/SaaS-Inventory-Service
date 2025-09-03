package com.saas.inventory.model.FixedAssetTransfer;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "fixed_asset_transfer_details")

public class FixedAssetTransferDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID itemId;
    private String tagNumber;


    @Column(nullable = false)
    private Integer quantity;


    @Column(nullable = false)
    private String remark;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_asset_transfer_id")
    private FixedAssetTransfer fixedAssetTransfer;


}