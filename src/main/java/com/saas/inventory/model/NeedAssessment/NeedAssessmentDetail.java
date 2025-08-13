package com.saas.inventory.model.NeedAssessment;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "need_assessment_detail")
public class NeedAssessmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "need_assessment_id")
    private NeedAssessment needAssessment;

    private UUID itemId;         // reference to item-service

    private UUID   generalLedgerId;  // snapshot
    private BigDecimal budgetAmount; // snapshot
}
