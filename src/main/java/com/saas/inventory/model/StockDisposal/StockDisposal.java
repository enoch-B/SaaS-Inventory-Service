package com.saas.inventory.model.StockDisposal;



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
@Table(name = "stock_disposal")
public class StockDisposal extends Base {

    private UUID storeId;

    @Column(nullable = false)
    private String disposalNo;

    @Column(nullable = false)
    private String disposalStatus;

    @Column(nullable = false)
    private LocalDate proposeDate;

    @Column(nullable = false)
    private LocalDate approvedDate;



    private String fileName;
    private String fileType;

    @Lob
    @Column(name = "file_Bytes", length = 5000, columnDefinition = "MEDIUMBLOB")
    private byte[] fileBytes;



    @OneToMany(mappedBy = "StockDisposal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockDisposalDetail> stockDisposalDetails; // Details of the disposed assets


}