package com.saas.inventory.model.NeedAssessment;


import com.saas.inventory.enums.PurchaseType;
import com.saas.inventory.model.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "need_assessment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeedAssessment  extends Base {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseType purchaseType; // GOODS, SERVICE, WORK

    private UUID departmentId; // from department-service
    private UUID storeId; // from store-service
    private UUID budgetYearId;



    @OneToMany(mappedBy = "needAssessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NeedAssessmentDetail> needAssessmentDetails;
}
