package com.saas.inventory.model.FixedAssetDisposal;


import com.saas.inventory.enums.DisposalStatus;
import com.saas.inventory.model.Base;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "fixed_asset_disposal")
public class FixedAssetDisposal extends Base {

    private UUID storeId; // from store-service


    @Column(nullable = false)
    private String fixedAssetDisposalNo;

    @Column(nullable = false)
    private LocalDate approvedDate;


    @Column(nullable = false, updatable = false)
    private LocalDate proposedDate;

    @Column(name = "disposal_status")
    private DisposalStatus disposalStatus;


    private String fileName;
    private String fileType;

    @Lob
    private byte[] fileBytes;


    // Join with DisposableAsset
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disposable_asset_id", nullable = false)
    private DisposableAsset disposableAsset;

    @OneToMany(mappedBy = "fixedAssetDisposal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedAssetDisposalDetail> disposalDetails;
}