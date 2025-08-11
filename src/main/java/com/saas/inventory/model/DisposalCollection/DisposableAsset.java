package com.saas.inventory.model.DisposalCollection;

import com.saas.inventory.enums.DisposableType;
import com.saas.inventory.enums.DisposalStatus;
import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "disposable_assets")

public class DisposableAsset extends Base {

    private String drNo;

    private UUID storeId;
    private UUID departmentId;

    @Enumerated(EnumType.STRING)
    private DisposableType disposableType; // FIXED_ASSET, NON_FIXED_ASSET, MERCHANDISED

    private LocalDate requisitionDate;

    @Enumerated(EnumType.STRING)
    private DisposalStatus disposalStatus; // PENDING, COMPLETED, etc.


    @OneToMany(mappedBy = "disposableAsset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisposableFixedAssetDetail> disposableAssetDetail;


}

