package com.saas.inventory.model.FixedAssetTransfer;

import com.saas.inventory.enums.TransferType;
import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fixed_asset_transfers")
@Data
public class FixedAssetTransfer extends Base {

    private UUID departmentId; //  to department-service


    @Column(nullable = false)
    private String transferNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferType transferType;

    @Column(nullable = false)
    private UUID transferFromId;

    @Column(nullable = false)
    private UUID transferToId;


    @OneToMany(mappedBy = "fixedAssetTransfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedAssetTransferDetail> transferDetails;


}

