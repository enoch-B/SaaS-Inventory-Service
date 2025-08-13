package com.saas.inventory.model.InventoryCountSheet;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "inventory_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    private UUID tenantId;

    @Column(nullable = false)
    private UUID itemId;  // From item-service

    private int quantity;
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_count_id")
    private InventoryCount inventoryCount;
}
