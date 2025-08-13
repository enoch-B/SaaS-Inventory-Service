package com.saas.inventory.repository.NeedAssessment;

import com.saas.inventory.model.NeedAssessment.NeedAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NeedAssessmentRepository extends JpaRepository<NeedAssessment, UUID> {
    Page<NeedAssessment> findByTenantId(UUID tenantId, Pageable pageable);
}