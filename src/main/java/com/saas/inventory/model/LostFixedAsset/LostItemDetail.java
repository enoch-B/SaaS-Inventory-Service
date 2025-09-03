package com.saas.inventory.model.LostFixedAsset;



import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "lost_item_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LostItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID itemId;




    @Column(nullable = false)
    private String duration;      // as string or better → `int` + unit

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String remark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lost_fixed_asset_id")
    private LostFixedAsset lostFixedAsset;
}
