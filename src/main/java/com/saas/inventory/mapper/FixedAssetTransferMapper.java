package com.saas.inventory.mapper;

import com.saas.inventory.dto.clientDto.DepartmentDto;
import com.saas.inventory.dto.clientDto.EmployeeDto;
import com.saas.inventory.dto.clientDto.FixedAssetDto;
import com.saas.inventory.dto.request.FixedAssetTransfer.FixedAssetTransferRequest;
import com.saas.inventory.dto.response.FixedAssetTransfer.FixedAssetTransferDetailResponse;
import com.saas.inventory.dto.response.FixedAssetTransfer.FixedAssetTransferResponse;
import com.saas.inventory.model.FixedAssetTransfer.FixedAssetTransfer;
import com.saas.inventory.model.FixedAssetTransfer.FixedAssetTransferDetail;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FixedAssetTransferMapper {

    private final ValidationUtil validationUtil;

    public FixedAssetTransfer toEntity(UUID tenantId,FixedAssetTransferRequest request){

        FixedAssetTransfer fixedAssetTransfer = new FixedAssetTransfer();

//        DepartmentDto department=validationUtil.getDepartmentById(tenantId,request.getDepartmentId());
//        EmployeeDto toEmployee=validationUtil.getEmployeeById(tenantId,request.getTransferToId());
//        EmployeeDto fromEmployee=validationUtil.getEmployeeById(tenantId,request.getTransferFromId());


        fixedAssetTransfer.setTenantId(tenantId);
        fixedAssetTransfer.setTransferNo(request.getTransferNo());
        fixedAssetTransfer.setDepartmentId(request.getDepartmentId());
        fixedAssetTransfer.setTransferToId(request.getTransferToId());
        fixedAssetTransfer.setTransferFromId(request.getTransferFromId());
        fixedAssetTransfer.setTransferType(request.getTransferType());
        if(request.getTransferDetails() != null){
            List<FixedAssetTransferDetail> details = request.getTransferDetails().stream().map(detailReq ->{
                FixedAssetTransferDetail detail = new FixedAssetTransferDetail();

//                FixedAssetDto item=validationUtil.getAssetById(tenantId,detailReq.getItemId());

                detail.setItemId(detailReq.getItemId());
                detail.setQuantity(detailReq.getQuantity());
                detail.setRemark(detailReq.getRemark());
                detail.setDescription(detailReq.getDescription());
                detail.setFixedAssetTransfer(fixedAssetTransfer);
                return detail;
            }).collect(Collectors.toList());

            fixedAssetTransfer.setTransferDetails(details);

        }
        return  fixedAssetTransfer;


    }

    public FixedAssetTransferResponse toResponse(FixedAssetTransfer assetTransfer){
        FixedAssetTransferResponse response = new FixedAssetTransferResponse();
        response.setId(assetTransfer.getId());
        response.setTenantId(assetTransfer.getTenantId());
        response.setDepartmentId(assetTransfer.getDepartmentId());
        response.setTransferToId(assetTransfer.getTransferToId());
        response.setTransferFromId(assetTransfer.getTransferFromId());
        response.setTransferNo(assetTransfer.getTransferNo());
        response.setTransferType(assetTransfer.getTransferType());
        response.setCreatedAt(assetTransfer.getCreatedAt());
        response.setUpdatedAt(assetTransfer.getUpdatedAt());
        response.setCreatedBy(assetTransfer.getCreatedBy());
        response.setUpdatedBy(assetTransfer.getUpdatedBy());

        if (assetTransfer.getTransferDetails() != null) {
            List<FixedAssetTransferDetailResponse> details = assetTransfer.getTransferDetails().stream()
                    .map(detail -> {
                        FixedAssetTransferDetailResponse detailResponse = new FixedAssetTransferDetailResponse();
                        detailResponse.setItemId(detail.getItemId());
                        detailResponse.setQuantity(detail.getQuantity());
                        detailResponse.setRemark(detail.getRemark());
                        detailResponse.setDescription(detail.getDescription());
                        return detailResponse;
                    }).collect(Collectors.toList());

            response.setTransferDetails(details);
        }

        return response;
    }

    public FixedAssetTransfer updateFixedAssetTransfer(UUID tenantId,FixedAssetTransfer assetTransfer, FixedAssetTransferRequest request) {

//        DepartmentDto department=validationUtil.getDepartmentById(tenantId,request.getDepartmentId());
//        EmployeeDto toEmployee=validationUtil.getEmployeeById(tenantId,request.getTransferToId());
//        EmployeeDto fromEmployee=validationUtil.getEmployeeById(tenantId,request.getTransferFromId());


        if (request.getDepartmentId() != null) {
            assetTransfer.setDepartmentId(request.getDepartmentId());
        }

        if (request.getTransferType() != null) {
            assetTransfer.setTransferType(request.getTransferType());
        }

        if (request.getTransferFromId() != null) {
            assetTransfer.setTransferFromId(request.getTransferFromId());
        }

        if (request.getTransferToId() != null) {
            assetTransfer.setTransferToId(request.getTransferToId());
        }

        if (request.getTransferDetails() != null) {
            List<FixedAssetTransferDetail> details = request.getTransferDetails().stream().map(detailReq -> {
                FixedAssetTransferDetail assetTransferDetail = new FixedAssetTransferDetail();

//                FixedAssetDto item = validationUtil.getAssetById(tenantId, detailReq.getItemId());

                assetTransferDetail.setItemId(detailReq.getItemId());
                assetTransferDetail.setQuantity(detailReq.getQuantity());
                assetTransferDetail.setRemark(detailReq.getRemark());
                assetTransferDetail.setDescription(detailReq.getDescription());
                assetTransferDetail.setFixedAssetTransfer(assetTransfer);
                return assetTransferDetail;
            }).toList();

            assetTransfer.getTransferDetails().clear();
            assetTransfer.getTransferDetails().addAll(details);
        }

        return assetTransfer;
    }




}

