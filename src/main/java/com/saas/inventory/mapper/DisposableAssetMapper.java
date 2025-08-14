package com.saas.inventory.mapper;

import com.saas.inventory.dto.clientDto.DepartmentDto;
import com.saas.inventory.dto.clientDto.FixedAssetDto;
import com.saas.inventory.dto.clientDto.StoreDto;
import com.saas.inventory.dto.request.DisposableAsset.DisposableAssetRequest;
import com.saas.inventory.dto.response.DiposalCollection.DisposableAssetResponse;
import com.saas.inventory.dto.response.DiposalCollection.DisposableFixedAssetDetailResponse;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.model.DisposalCollection.DisposableFixedAssetDetail;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DisposableAssetMapper {

    private final ValidationUtil validationUtil;

    public DisposableAsset toEntity(UUID tenantId, DisposableAssetRequest request)
    throws IOException {
        DisposableAsset disposableAsset = new DisposableAsset();

//        DepartmentDto department=validationUtil.getDepartmentById(tenantId,request.getDepartmentId());
//        StoreDto store=validationUtil.getStoreById(tenantId,request.getStoreId());

        disposableAsset.setTenantId(tenantId);
        disposableAsset.setDisposableType(request.getDisposableType());
        disposableAsset.setDisposalStatus(request.getDisposalStatus());
        disposableAsset.setDrNo(request.getDrNo());
        disposableAsset.setDepartmentId(request.getDepartmentId());
        disposableAsset.setStoreId(request.getStoreId());
        disposableAsset.setRequisitionDate(request.getRequisitionDate());

        if (request.getDisposableFixedAssetDetails() != null) {
            List<DisposableFixedAssetDetail> details = request.getDisposableFixedAssetDetails().stream().map(detailRequest -> {
                DisposableFixedAssetDetail detail = new DisposableFixedAssetDetail();

//                FixedAssetDto asset=validationUtil.getItemById(tenantId,detailRequest.getItemId());

                detail.setItemId(detailRequest.getItemId());
                detail.setBatchNo(detailRequest.getBatchNo());
                detail.setDescription(detailRequest.getDescription());
                detail.setExpirationDate(detailRequest.getExpirationDate());
                detail.setQuantity(detailRequest.getQuantity());
                detail.setDisposableAsset(disposableAsset);

                return detail;

            }).collect(Collectors.toList());
            disposableAsset.setDisposableAssetDetail(details);

        }
        return disposableAsset;

    }

    //Map the entity to ResponseDto

    public DisposableAssetResponse toResponse(DisposableAsset disposableAsset) {
        DisposableAssetResponse response = new DisposableAssetResponse();
        response.setId(disposableAsset.getId());
        response.setTenantId(disposableAsset.getTenantId());
        response.setStoreId(disposableAsset.getStoreId());
        response.setDisposableType(disposableAsset.getDisposableType());
        response.setDisposalStatus(disposableAsset.getDisposalStatus());
        response.setRequisitionDate(disposableAsset.getRequisitionDate());
        response.setDrNo(disposableAsset.getDrNo());
        response.setDepartmentId(disposableAsset.getDepartmentId());
        response.setCreatedAt(disposableAsset.getCreatedAt());
        response.setUpdatedAt(disposableAsset.getUpdatedAt());
        response.setUpdatedBy(disposableAsset.getUpdatedBy());
        response.setCreatedBy(disposableAsset.getCreatedBy());

        if (disposableAsset.getDisposableAssetDetail() != null) {
            List<DisposableFixedAssetDetailResponse> details = disposableAsset.getDisposableAssetDetail().stream().map(disposableFixedAssetDetail -> {
                DisposableFixedAssetDetailResponse detailResponse = new DisposableFixedAssetDetailResponse();
                detailResponse.setItemId(disposableFixedAssetDetail.getItemId());
                detailResponse.setBatchNo(disposableFixedAssetDetail.getBatchNo());
                detailResponse.setDescription(disposableFixedAssetDetail.getDescription());
                detailResponse.setQuantity(disposableFixedAssetDetail.getQuantity());
                detailResponse.setExpirationDate(disposableFixedAssetDetail.getExpirationDate());

                return detailResponse;

            }).collect(Collectors.toList());

            response.setDisposableFixedAssetDetails(details);

        }
        return response;


    }

    public void updateDisposableAssetFromRequest(DisposableAssetRequest disposableAssetRequest, DisposableAsset disposableAsset) {

        if (disposableAssetRequest.getDisposableType() != null)
            disposableAsset.setDisposableType(disposableAssetRequest.getDisposableType());

        if (disposableAssetRequest.getDisposalStatus() != null)
            disposableAsset.setDisposalStatus(disposableAssetRequest.getDisposalStatus());

        if (disposableAssetRequest.getDrNo() != null)
            disposableAsset.setDrNo(disposableAssetRequest.getDrNo());

        if (disposableAssetRequest.getDepartmentId() != null)
            disposableAsset.setDepartmentId(disposableAssetRequest.getDepartmentId());

        if (disposableAssetRequest.getStoreId() != null)
            disposableAsset.setStoreId(disposableAssetRequest.getStoreId());

        if (disposableAssetRequest.getRequisitionDate() != null)
            disposableAsset.setRequisitionDate(disposableAssetRequest.getRequisitionDate());

        // Update detail list if provided
        if (disposableAssetRequest.getDisposableFixedAssetDetails() != null && !disposableAssetRequest.getDisposableFixedAssetDetails().isEmpty()) {
            List<DisposableFixedAssetDetail> details = disposableAssetRequest.getDisposableFixedAssetDetails()
                    .stream()
                    .map(detailRequest -> {
                        DisposableFixedAssetDetail detail = new DisposableFixedAssetDetail();
                        detail.setItemId(detailRequest.getItemId());
                        detail.setBatchNo(detailRequest.getBatchNo());
                        detail.setDescription(detailRequest.getDescription());
                        detail.setExpirationDate(detailRequest.getExpirationDate());
                        detail.setQuantity(detailRequest.getQuantity());
                        detail.setDisposableAsset(disposableAsset); // link to parent
                        return detail;
                    })
                    .toList();

            disposableAsset.getDisposableAssetDetail().clear();
            disposableAsset.getDisposableAssetDetail().addAll(details);
        }

    }

}


