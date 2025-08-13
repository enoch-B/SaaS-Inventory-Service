package com.saas.inventory.model.NeedAssessmentInfo;

import com.saas.inventory.enums.AssessmentType;
import com.saas.inventory.model.Base;
import com.saas.inventory.model.NeedAssessment.NeedAssessment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "need_assessment_summaries")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NeedAssessmentSummary extends Base {

    @Enumerated(EnumType.STRING)
    private AssessmentType assessmentType;

    private UUID storeId;
    private UUID budgetYearId;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "need_assessment_summary_id")
    List<NeedAssessment> needAssessments;
}
