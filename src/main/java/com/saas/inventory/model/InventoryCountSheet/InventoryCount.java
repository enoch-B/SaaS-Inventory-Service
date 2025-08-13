package com.saas.inventory.model.InventoryCountSheet;

import com.saas.inventory.enums.CountType;
import com.saas.inventory.enums.StoreType;
import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "inventory_counts")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class InventoryCount extends Base {


    @Column(nullable = false, unique = true)
    private String inventoryCountNumber;

    @Column(nullable = false)
    private UUID storeId; // From store-service

    private UUID preparedById; // From employee-service

    private UUID committeeId; // FK to committee-service


    @ElementCollection
    @CollectionTable(name = "inventory_count_committee_members", joinColumns = @JoinColumn(name = "inventory_count_id"))
    @Column(name = "member_id")
    private List<UUID> committeeMembersId; // From employee-service


    @Enumerated(EnumType.STRING)
    private CountType countType;    // e.g., Periodic, Perpetual

    @Enumerated(EnumType.STRING)
    private StoreType storeType; // e.g., Internal, Merchandise

    private UUID budgetYearId;
    private LocalDate countDate;

    @OneToMany(mappedBy = "inventoryCount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryDetail> inventoryDetails;
}

