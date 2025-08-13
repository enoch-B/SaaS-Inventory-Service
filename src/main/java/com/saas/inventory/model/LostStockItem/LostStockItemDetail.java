package com.saas.inventory.model.LostStockItem;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LostStockItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "lost_stock_item_id")
    private LostStockItem lostStockItem;

    private UUID itemId;        // FK to item-service
    private Integer Quantity;

    private String description;
    private String duration;
    private String remark;
}
