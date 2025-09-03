package com.saas.inventory.model.LostStockItem;

import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lost_stock_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostStockItem extends Base {


    private String lostStockItemNo;

    private String status;

    private LocalDate registrationDate;

    private String region;
    private UUID departmentId;
    private UUID storeId;

    private UUID committeeId;

    @Column(name = "member_id")
    private List<UUID> committeeMembersId;


    // Relations
    @OneToMany(mappedBy = "lostStockItem", cascade = CascadeType.ALL)
    private List<LostStockItemDetail> lostStockItemDetails;

    // File attachment
    private String fileName;
    private String fileType;

    @Lob
    @Column(name="data", columnDefinition = "MEDIUMBLOB")
    private byte[] FileBytes; // File data as a byte array



}