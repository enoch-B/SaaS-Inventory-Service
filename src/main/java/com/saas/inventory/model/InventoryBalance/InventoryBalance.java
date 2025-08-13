package com.saas.inventory.model.InventoryBalance;



import com.saas.inventory.enums.StoreType;
import com.saas.inventory.model.Base;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Table(name = "inventory_balance")
@NoArgsConstructor
@Data
public class InventoryBalance extends Base {

    @Enumerated(EnumType.STRING)
    private StoreType storeType;


    // FK to Count Sheet
    @ManyToOne
    @JoinColumn(name = "inventory_count_id", nullable = false)
    private InventoryCount inventoryCount;

    @OneToMany(mappedBy = "inventoryBalance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryBalanceItem> inventoryBalanceItem;
}
