package com.saas.inventory.mapper;

import com.saas.inventory.dto.clientDto.DepartmentDto;
import com.saas.inventory.dto.clientDto.EmployeeDto;
import com.saas.inventory.dto.clientDto.FixedAssetDto;
import com.saas.inventory.dto.clientDto.StoreDto;
import com.saas.inventory.dto.request.FixedAssetReturn.FixedAssetReturnRequest;
import com.saas.inventory.dto.response.FixedAssetReturn.FixedAssetReturnDetailResponse;
import com.saas.inventory.dto.response.FixedAssetReturn.FixedAssetReturnResponse;
import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturn;
import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturnDetail;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FixedAssetReturnMapper {

      private final ValidationUtil validationUtil;

    public FixedAssetReturn toEntity(UUID tenantId,FixedAssetReturnRequest request) {
        FixedAssetReturn assetReturn = new FixedAssetReturn();


        DepartmentDto department=validationUtil.getDepartmentById(tenantId,request.getDepartmentId());
        StoreDto store=validationUtil.getStoreById(tenantId,request.getStoreId());
        EmployeeDto employeeDto=validationUtil.getEmployeeById(tenantId,request.getReturnedById());


        assetReturn.setTenantId(tenantId);
        assetReturn.setDepartmentId(department.getId());
        assetReturn.setStoreId(store.getId());
        assetReturn.setAssetReturnNo(request.getAssetReturnNo());
        assetReturn.setReturnedById(employeeDto.getId());
        assetReturn.setReturnStatus(request.getReturnStatus());
        assetReturn.setReceivedDate(request.getReceivedDate());
        assetReturn.setReturnedDate(request.getReturnedDate());

        if (request.getReturnDetailRequests() != null) {
            List<FixedAssetReturnDetail> details = request.getReturnDetailRequests().stream().map(detailReq -> {
                FixedAssetReturnDetail detail = new FixedAssetReturnDetail();

                FixedAssetDto fixedAssetDto=validationUtil.getAssetById(tenantId, detailReq.getItemId());

                detail.setItemId(fixedAssetDto.getId());
                detail.setItemStatus(detailReq.getItemStatus());
                detail.setDescription(detailReq.getDescription());
                detail.setFixedAssetReturn(assetReturn); // Link to parent
                return detail;
            }).collect(Collectors.toList());
            assetReturn.setReturnDetails(details);
        }

        return assetReturn;
    }


    public FixedAssetReturnResponse toResponse(FixedAssetReturn entity) {
        FixedAssetReturnResponse response = new FixedAssetReturnResponse();
        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setDepartmentId(entity.getDepartmentId());
        response.setStoreId(entity.getStoreId());
        response.setReturnedById(entity.getReturnedById());
        response.setReturnStatus(entity.getReturnStatus());
        response.setReceivedDate(entity.getReceivedDate());
        response.setReturnedDate(entity.getReturnedDate());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());

        if (entity.getReturnDetails() != null) {
            List<FixedAssetReturnDetailResponse> detailResponses = entity.getReturnDetails().stream()
                    .map(detail -> {
                        FixedAssetReturnDetailResponse returnDetailResponse = new FixedAssetReturnDetailResponse();
                        returnDetailResponse.setItemId(detail.getItemId());
                        returnDetailResponse.setItemStatus(detail.getItemStatus());
                        returnDetailResponse.setDescription(detail.getDescription());
                        return returnDetailResponse;
                    }).collect(Collectors.toList());
            response.setReturnDetails(detailResponses);
        }

        return response;
    }

    public FixedAssetReturn updateFixedAssetReturn(UUID tenantId,FixedAssetReturn assetReturn, FixedAssetReturnRequest request) {

        DepartmentDto department=validationUtil.getDepartmentById(tenantId,request.getDepartmentId());
        StoreDto store=validationUtil.getStoreById(tenantId,request.getStoreId());
        EmployeeDto employeeDto=validationUtil.getEmployeeById(tenantId,request.getReturnedById());


        if (request.getDepartmentId() != null) {
            assetReturn.setDepartmentId(department.getId());
        }
        if (request.getStoreId() != null) {
            assetReturn.setStoreId(store.getId());
        }

        if (request.getReturnedById() != null) {
            assetReturn.setReturnedById(employeeDto.getId());
        }

        if (request.getReturnStatus() != null) {
            assetReturn.setReturnStatus(request.getReturnStatus());
        }
        if (request.getReceivedDate() != null) {
            assetReturn.setReceivedDate(request.getReceivedDate());
        }
        if (request.getReturnedDate() != null) {
            assetReturn.setReturnedDate(request.getReturnedDate());
        }

        if (request.getReturnDetailRequests() != null) {
            List<FixedAssetReturnDetail> details = request.getReturnDetailRequests().stream().map(detailReq -> {
                FixedAssetReturnDetail detail = new FixedAssetReturnDetail();

                FixedAssetDto fixedAssetDto=validationUtil.getAssetById(tenantId, detailReq.getItemId());

                detail.setItemId(fixedAssetDto.getId());
                detail.setItemStatus(detailReq.getItemStatus());
                detail.setDescription(detailReq.getDescription());
                detail.setFixedAssetReturn(assetReturn);
                return detail;
            }).toList();

            assetReturn.getReturnDetails().clear();
            assetReturn.getReturnDetails().addAll(details);
        }


        return assetReturn;

    }

}
