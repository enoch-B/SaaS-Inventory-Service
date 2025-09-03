package com.saas.inventory.model.InventoryBalance;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "inventory_balance_items")
public class InventoryBalanceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID itemId;

    private BigDecimal quantity;

    private BigDecimal binBalance;  // Pulled from Item Service during form generation

    private BigDecimal difference;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "inventory_balance_id")
    private InventoryBalance inventoryBalance;


}
