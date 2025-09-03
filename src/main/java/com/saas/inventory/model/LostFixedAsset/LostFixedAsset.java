package com.saas.inventory.model.LostFixedAsset;

import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lost_fixed_assets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LostFixedAsset extends Base {


    @Column(nullable = false, unique = true)
    private String lostItemNo;

    private UUID storeId;
    private UUID   departmentId;




    @Column(nullable = false)
    private LocalDate registrationDate;


    private String fileName;
    private String fileType;

    @Lob
    @Column(name="data",length = 50000, columnDefinition = "MEDIUMBLOB")
    private byte[] FileBytes;


    @OneToMany(mappedBy = "lostFixedAsset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LostItemDetail> lostItemDetails;


}
