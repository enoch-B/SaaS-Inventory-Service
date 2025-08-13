package com.saas.inventory.repository.NeedAssessment;

import com.saas.inventory.model.NeedAssessment.NeedAssessmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NeedAssessmentDetailRepository extends JpaRepository<NeedAssessmentDetail, UUID> {
}
