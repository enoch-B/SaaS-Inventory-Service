package com.saas.inventory.model.FixedAssetReturn;

import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "fixed_asset_return")

public class FixedAssetReturn extends Base {

    private UUID departmentId;
    private UUID storeId;

    private UUID returnedById;

    @Column(nullable = false, unique = true)
    private String assetReturnNo;


    @Column(nullable = false)
    private String returnStatus;

    private LocalDate receivedDate;
    private LocalDate returnedDate;



    @OneToMany(mappedBy = "fixedAssetReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedAssetReturnDetail> returnDetails; // Details of the returned assets

}