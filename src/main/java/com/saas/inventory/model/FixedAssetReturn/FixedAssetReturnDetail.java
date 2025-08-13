package com.saas.inventory.model.FixedAssetReturn;

import com.saas.inventory.enums.ItemStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "fixed_asset_return_details")
public class FixedAssetReturnDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    private UUID itemId; // from another service

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus; //

    private String tagNumber;

    @Column(nullable = false)
    private String description;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_asset_return_id")
    private FixedAssetReturn fixedAssetReturn; // the return this detail belongs to
}
