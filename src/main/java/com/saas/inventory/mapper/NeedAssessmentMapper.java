package com.saas.inventory.mapper;


import com.saas.inventory.dto.clientDto.*;
import com.saas.inventory.dto.request.NeedAssessment.NeedAssessmentRequest;
import com.saas.inventory.dto.response.NeedAssessment.NeedAssessmentDetailResponse;
import com.saas.inventory.dto.response.NeedAssessment.NeedAssessmentResponse;
import com.saas.inventory.model.NeedAssessment.NeedAssessment;
import com.saas.inventory.model.NeedAssessment.NeedAssessmentDetail;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class NeedAssessmentMapper {

    private final ValidationUtil validationUtil;

    public NeedAssessment toEntity(UUID tenantId,NeedAssessmentRequest request){
        NeedAssessment entity = new NeedAssessment();

//        DepartmentDto department=validationUtil.getDepartmentById(tenantId, request.getDepartmentId());
//        StoreDto store = validationUtil.getStoreById(tenantId, request.getStoreId());
//        BudgetDto budget= validationUtil.getBudgetYearById(tenantId,request.getBudgetYearId());


        entity.setTenantId(tenantId);
        entity.setStoreId(request.getStoreId());
        entity.setPurchaseType(request.getPurchaseType());
        entity.setBudgetYearId(request.getBudgetYearId());
        entity.setDepartmentId(request.getDepartmentId());

        if(request.getAssessmentDetail() != null) {
            List<NeedAssessmentDetail> details = request.getAssessmentDetail().stream().map(detailRequest ->{
                NeedAssessmentDetail detail = new NeedAssessmentDetail();

                ItemDto item = validationUtil.getItemById(tenantId, detailRequest.getItemId());

                detail.setItemId(item.getId());
                detail.setGeneralLedgerId(detailRequest.getGeneralLedger());
                detail.setBudgetAmount(detailRequest.getBudgetAmount());
                detail.setNeedAssessment(entity);


                return detail;
            }).collect(Collectors.toList());

            entity.setNeedAssessmentDetails(details);
        }
        return entity;

    }

    public NeedAssessmentResponse toResponse(NeedAssessment assessment){
        NeedAssessmentResponse response = new NeedAssessmentResponse();
        response.setId(assessment.getId());
        response.setTenantId(assessment.getTenantId());
        response.setDepartmentId(assessment.getDepartmentId());
        response.setStoreId(assessment.getStoreId());
        response.setPurchaseType(assessment.getPurchaseType());
        response.setBudgetYearId(assessment.getBudgetYearId());
        response.setCreatedAt(assessment.getCreatedAt());
        response.setUpdatedAt(assessment.getUpdatedAt());
        response.setUpdatedBy(assessment.getUpdatedBy());
        response.setCreatedBy(assessment.getCreatedBy());

        if(assessment.getNeedAssessmentDetails() != null){
            List<NeedAssessmentDetailResponse> details = assessment.getNeedAssessmentDetails().stream().map(detail ->{
                NeedAssessmentDetailResponse detailResponse = new NeedAssessmentDetailResponse();
                detailResponse.setItemId(detail.getItemId());
                detailResponse.setGeneralLedger(detail.getGeneralLedgerId());
                detailResponse.setBudgetAmount(detail.getBudgetAmount());

                return detailResponse;
            }).collect(Collectors.toList());

            response.setAssessmentResponse(details);
        }

        return response;
    }


    public NeedAssessment updateNeedAssessmentFromRequest(UUID tenantId,NeedAssessmentRequest request, NeedAssessment needAssessment) throws IOException {
//        DepartmentDto department=validationUtil.getDepartmentById(tenantId, request.getDepartmentId());
//        StoreDto store = validationUtil.getStoreById(tenantId, request.getStoreId());
//        BudgetDto budget= validationUtil.getBudgetYearById(tenantId,request.getBudgetYearId());

        if (request.getStoreId() != null) {
            needAssessment.setStoreId(request.getStoreId());
        }

        if (request.getPurchaseType() != null) {
            needAssessment.setPurchaseType(request.getPurchaseType());
        }

        if (request.getBudgetYearId() != null) {
            needAssessment.setBudgetYearId(request.getBudgetYearId());
        }

        if (request.getDepartmentId() != null) {
            needAssessment.setDepartmentId(request.getDepartmentId());
        }

        if (request.getAssessmentDetail() != null) {
            List<NeedAssessmentDetail> details = request.getAssessmentDetail().stream()
                    .map(detailRequest -> {
                        NeedAssessmentDetail detail = new NeedAssessmentDetail();
//                        ItemDto item = validationUtil.getItemById(tenantId, detailRequest.getItemId());

                        detail.setItemId(detailRequest.getItemId());
                        detail.setGeneralLedgerId(detailRequest.getGeneralLedger());
                        detail.setBudgetAmount(detailRequest.getBudgetAmount());
                        detail.setNeedAssessment(needAssessment);
                        return detail;
                    }).toList();

            needAssessment.getNeedAssessmentDetails().clear();
            needAssessment.getNeedAssessmentDetails().addAll(details);
        }
    return needAssessment;
    }




}

