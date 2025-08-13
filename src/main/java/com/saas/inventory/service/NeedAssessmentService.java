package com.saas.inventory.service;

import com.saas.inventory.dto.request.NeedAssessment.NeedAssessmentRequest;
import com.saas.inventory.dto.response.NeedAssessment.NeedAssessmentResponse;
import com.saas.inventory.mapper.NeedAssessmentMapper;
import com.saas.inventory.model.NeedAssessment.NeedAssessment;
import com.saas.inventory.repository.NeedAssessment.NeedAssessmentDetailRepository;
import com.saas.inventory.repository.NeedAssessment.NeedAssessmentRepository;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NeedAssessmentService {

    private final NeedAssessmentRepository needAssessmentRepository;
    private final NeedAssessmentDetailRepository needAssessmentDetailRepository;
    private final NeedAssessmentMapper needAssessmentMapper;
    private final ValidationUtil validationUtil;

    public NeedAssessmentResponse addNeedAssessment(UUID tenantId, NeedAssessmentRequest needAssessmentRequest) {

        NeedAssessment needAssessment = needAssessmentMapper.toEntity(tenantId,needAssessmentRequest);
        NeedAssessment savedAssessment = needAssessmentRepository.save(needAssessment);

        return needAssessmentMapper.toResponse(savedAssessment);

    }

    /*
  Get paginated Need Assessment
 */
    public Page<NeedAssessmentResponse> getAllNeedAssessmentPaginated(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return needAssessmentRepository.findByTenantId(tenantId, pageable)
                .map(needAssessmentMapper::toResponse);
    }

    /*
     *  Get single Need Assessment By id
     */

    public NeedAssessmentResponse getNeedAssessmentById(UUID tenantId, UUID id) {
        NeedAssessment needAssessment=needAssessmentRepository.findById(id)
                .filter(na-> na.getTenantId().equals(tenantId))
                .orElseThrow(()-> new RuntimeException("Need Assessment Not Found Or Tenant Mismatch"));
        return needAssessmentMapper.toResponse(needAssessment);
    }

    /*
      Delete Need Assessment
     */

    public void deleteNeedAssessment(UUID tenantId, UUID id) {

        NeedAssessment needAssessment = needAssessmentRepository.findById(id)
                .filter(na-> na.getTenantId().equals(tenantId))
                .orElseThrow(()-> new RuntimeException("Need Assessment Not Found or Tenant Mismatch"));

        needAssessmentRepository.delete(needAssessment);
    }

    public NeedAssessmentResponse updateNeedAssessment(UUID tenantId,
                                                       UUID assessmentId,
                                                       NeedAssessmentRequest request) throws IOException {
        //  Validate & retrieve the entity
        NeedAssessment existing = validationUtil.getNeedAssessmentById(tenantId, assessmentId);

        // Update using mapper
        existing = needAssessmentMapper.updateNeedAssessmentFromRequest(tenantId,request, existing);

        //  Save updated object
        existing = needAssessmentRepository.save(existing);
        return needAssessmentMapper.toResponse(existing);
    }

}
