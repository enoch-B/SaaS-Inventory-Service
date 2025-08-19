package com.saas.inventory.mapper;

import com.saas.inventory.dto.clientDto.FixedAssetDto;
import com.saas.inventory.dto.clientDto.StoreDto;
import com.saas.inventory.dto.request.FixedAssetDisposal.FixedAssetDisposalRequest;
import com.saas.inventory.dto.response.FixedAssetDisposal.FixedAssetDisposalDetailResponse;
import com.saas.inventory.dto.response.FixedAssetDisposal.FixedAssetDisposalResponse;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.model.FixedAssetDisposal.FixedAssetDisposal;
import com.saas.inventory.model.FixedAssetDisposal.FixedAssetDisposalDetail;
import com.saas.inventory.utility.FileUtil;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class FixedAssetDisposalMapper {
    private final ValidationUtil validationUtil;

    public FixedAssetDisposal toEntity(DisposableAsset disposableAsset, UUID tenantId, FixedAssetDisposalRequest request, MultipartFile file)
    throws IOException {
        FixedAssetDisposal assetDisposal = new FixedAssetDisposal();
        StoreDto store=validationUtil.getStoreById(tenantId,request.getStoreId());

        assetDisposal.setTenantId(tenantId);
        assetDisposal.setStoreId(store.getId());
        assetDisposal.setFixedAssetDisposalNo(request.getFixedAssetDisposalNo());
        assetDisposal.setApprovedDate(request.getApprovedDate());
        assetDisposal.setProposedDate(request.getProposedDate());
        assetDisposal.setDisposableAsset(disposableAsset);




        if (request.getDisposalDetails() != null) {
            List<FixedAssetDisposalDetail> details = request.getDisposalDetails().stream().map(detailRequest -> {
                FixedAssetDisposalDetail detail = new FixedAssetDisposalDetail();

                FixedAssetDto asset=validationUtil.getAssetById(tenantId,detailRequest.getItemId());

                detail.setItemId(asset.getId());
                detail.setItemLocation(detailRequest.getItemLocation());
                detail.setDisposalMethod(detailRequest.getDisposalMethod());
                detail.setFixedAssetDisposal(assetDisposal);
                return detail;

            }).collect(toList());
            assetDisposal.setDisposalDetails(details);
        }
        return getFixedAssetDisposalFile(assetDisposal, file);
    }

    private FixedAssetDisposal getFixedAssetDisposalFile(FixedAssetDisposal assetDisposal, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            boolean isPdf = MediaType.APPLICATION_PDF_VALUE.equals(contentType);
            boolean isDocx = "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType);
            boolean isImage = contentType != null && contentType.startsWith("image/");

            if (!(isPdf || isDocx || isImage)) {
                throw new IllegalArgumentException("Only PDF, DOCX, and image files are allowed!");
            }

            assetDisposal.setFileName(file.getOriginalFilename());
            assetDisposal.setFileType(file.getContentType());
            assetDisposal.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }

        return assetDisposal;
    }



    public FixedAssetDisposalResponse toResponse(FixedAssetDisposal assetDisposal) {
        FixedAssetDisposalResponse response = new FixedAssetDisposalResponse();
        response.setId(assetDisposal.getId());
        response.setTenantId(assetDisposal.getTenantId());
        response.setStoreId(assetDisposal.getStoreId());

        response.setProposedDate(assetDisposal.getProposedDate());
        response.setApprovedDate(assetDisposal.getApprovedDate());
        response.setFileName(assetDisposal.getFileName());
        response.setFileType(assetDisposal.getFileType());
        response.setFileData(assetDisposal.getFileBytes());

        response.setCreatedAt(assetDisposal.getCreatedAt());
        response.setCreatedBy(assetDisposal.getCreatedBy());
        response.setUpdatedAt(assetDisposal.getUpdatedAt());
        response.setUpdatedBy(assetDisposal.getUpdatedBy());

        if(assetDisposal.getDisposalDetails() != null) {
            List<FixedAssetDisposalDetailResponse> details = assetDisposal.getDisposalDetails().stream().map(detail -> {
                FixedAssetDisposalDetailResponse detailResponse = new FixedAssetDisposalDetailResponse();
                detailResponse.setItemId(detail.getItemId());
                detailResponse.setTagNumber(detail.getTagNumber());
                detailResponse.setItemLocation(detail.getItemLocation());
                detailResponse.setDisposalMethod(detail.getDisposalMethod());

                return detailResponse;
            }).collect(toList());

            response.setDisposalDetailResponses(details);

        }
        return response;

    }

    // Update assetDisposal with request data
    public FixedAssetDisposal updateEntity(UUID tenantId, FixedAssetDisposal assetDisposal, FixedAssetDisposalRequest request, MultipartFile file, DisposableAsset disposableAsset)
    throws IOException{

        StoreDto store=validationUtil.getStoreById(tenantId,request.getStoreId());

        if (request.getStoreId() != null) {
            assetDisposal.setStoreId(store.getId());
        }

        if (request.getApprovedDate() != null) {
            assetDisposal.setApprovedDate(request.getApprovedDate());
        }

        if (request.getProposedDate() != null) {
            assetDisposal.setProposedDate(request.getProposedDate());
        }
       assetDisposal.setDisposableAsset(disposableAsset);

        if (request.getDisposalDetails() != null) {
            List<FixedAssetDisposalDetail> details = request.getDisposalDetails().stream().map(detailRequest -> {
                FixedAssetDisposalDetail detail = new FixedAssetDisposalDetail();

                FixedAssetDto asset=validationUtil.getAssetById(tenantId,detailRequest.getItemId());

                detail.setItemId(asset.getId());
                detail.setItemLocation(detailRequest.getItemLocation());
                detail.setDisposalMethod(detailRequest.getDisposalMethod());
                detail.setFixedAssetDisposal(assetDisposal); // Link back to parent
                return detail;
            }).toList();

            assetDisposal.getDisposalDetails().clear();
            assetDisposal.getDisposalDetails().addAll(details);
        }

        return getFixedAssetDisposalFile(assetDisposal, file);
    }

}



