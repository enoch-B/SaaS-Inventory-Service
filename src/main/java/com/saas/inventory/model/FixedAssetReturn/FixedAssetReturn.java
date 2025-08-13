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

    private UUID departmentId; //  to department issue
    private UUID storeId; //  to store-service

    private UUID returnedById; //  to user/employee-service for the person returning the asset

    @Column(nullable = false, unique = true)
    private String assetReturnNo; // Unique return number for tracking


    @Column(nullable = false)
    private String returnStatus; // Status of the return

    private LocalDate receivedDate; // Date when the asset was received
    private LocalDate returnedDate; // Date when the asset was returned



    @OneToMany(mappedBy = "fixedAssetReturn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedAssetReturnDetail> returnDetails; // Details of the returned assets

}