package com.saas.inventory.mapper;


import com.saas.inventory.dto.request.LostFixedAsset.LostFixedAssetRequest;
import com.saas.inventory.dto.response.LostFixedAsset.LostFixedAssetResponse;
import com.saas.inventory.dto.response.LostFixedAsset.LostItemDetailResponse;
import com.saas.inventory.model.LostFixedAsset.LostFixedAsset;
import com.saas.inventory.model.LostFixedAsset.LostItemDetail;
import com.saas.inventory.utility.FileUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LostFixedAssetMapper {

    public LostFixedAsset toEntity(UUID tenantId, LostFixedAssetRequest request, MultipartFile file)
    throws IOException {
        LostFixedAsset lostFixedAsset = new LostFixedAsset();
        lostFixedAsset.setTenantId(tenantId);
        lostFixedAsset.setStoreId(request.getStoreId());
        lostFixedAsset.setDepartmentId(request.getDepartmentId());
        lostFixedAsset.setLostItemNo(request.getLostItemNo());
        lostFixedAsset.setRegistrationDate(request.getRegistrationDate());

        if(request.getLostItemDetails() !=null) {
            List<LostItemDetail> details = request.getLostItemDetails().stream().map(detailRequest -> {
                LostItemDetail detail = new LostItemDetail();
                detail.setItemId(detailRequest.getItemId());
                detail.setTagNo(detailRequest.getTagNo());
                detail.setDuration(detailRequest.getDuration());
                detail.setDescription(detailRequest.getDescription());
                detail.setRemark(detailRequest.getRemark());
                detail.setLostFixedAsset(lostFixedAsset);
                return detail;

            }).collect(Collectors.toList());
            lostFixedAsset.setLostItemDetails(details);
        }
        return getLostFixedAssetFile(lostFixedAsset, file);
    }





    public LostFixedAssetResponse toResponse(LostFixedAsset lostFixedAsset) {
        LostFixedAssetResponse response = new LostFixedAssetResponse();
        response.setId(lostFixedAsset.getId());
        response.setTenantId(lostFixedAsset.getTenantId());
        response.setStoreId(lostFixedAsset.getStoreId());
        response.setDepartmentId(lostFixedAsset.getDepartmentId());
        response.setLostItemNo(lostFixedAsset.getLostItemNo());
        response.setRegistrationDate(lostFixedAsset.getRegistrationDate());
        response.setFileName(lostFixedAsset.getFileName());
        response.setFileType(lostFixedAsset.getFileType());
        response.setFileBytes(lostFixedAsset.getFileBytes());
        response.setCreatedAt(lostFixedAsset.getCreatedAt());
        response.setUpdatedAt(lostFixedAsset.getUpdatedAt());
        response.setUpdatedBy(lostFixedAsset.getUpdatedBy());
        response.setCreatedBy(lostFixedAsset.getCreatedBy());

        if (lostFixedAsset.getLostItemDetails() != null) {
            List<LostItemDetailResponse> details = lostFixedAsset.getLostItemDetails().stream().map(detail -> {
                LostItemDetailResponse detailResponse = new LostItemDetailResponse();

                detailResponse.setItemId(detail.getItemId());
                detailResponse.setTagNo(detail.getTagNo());
                detailResponse.setDuration(detail.getDuration());
                detailResponse.setDescription(detail.getDescription());
                detailResponse.setRemark(detail.getRemark());
                return detailResponse;
            }).collect(Collectors.toList());

            response.setLostFixedAssetDetails(details);
        }
        return response;

    }


    public LostFixedAsset updateLostFixedAsset(LostFixedAsset lostFixedAsset,
                                               LostFixedAssetRequest request,
                                               MultipartFile file) throws IOException {

        if (request.getStoreId() != null) {
            lostFixedAsset.setStoreId(request.getStoreId());
        }

        if (request.getDepartmentId() != null) {
            lostFixedAsset.setDepartmentId(request.getDepartmentId());
        }


        if (request.getLostItemNo() != null) {
            lostFixedAsset.setLostItemNo(request.getLostItemNo());
        }

        if (request.getRegistrationDate() != null) {
            lostFixedAsset.setRegistrationDate(request.getRegistrationDate());
        }

        if (request.getFileType() != null) {
            lostFixedAsset.setFileType(request.getFileType());
        }

        if (request.getFileName() != null) {
            lostFixedAsset.setFileName(request.getFileName());
        }

        if (request.getData() != null) {
            lostFixedAsset.setFileBytes(request.getData());
        }

        if (request.getLostItemDetails() != null) {
            List<LostItemDetail> details = request.getLostItemDetails().stream().map(detailRequest -> {
                LostItemDetail detail = new LostItemDetail();
                detail.setItemId(detailRequest.getItemId());
                detail.setTagNo(detailRequest.getTagNo());
                detail.setDuration(detailRequest.getDuration());
                detail.setDescription(detailRequest.getDescription());
                detail.setRemark(detailRequest.getRemark());
                detail.setLostFixedAsset(lostFixedAsset);
                return detail;
            }).toList();

            lostFixedAsset.getLostItemDetails().clear();
            lostFixedAsset.getLostItemDetails().addAll(details);
        }

        return getLostFixedAssetFile(lostFixedAsset, file);
    }



    private LostFixedAsset getLostFixedAssetFile(LostFixedAsset lostFixedAsset, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            boolean isPdf = MediaType.APPLICATION_PDF_VALUE.equals(contentType);
            boolean isDocx = "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType);
            boolean isImage = contentType != null && contentType.startsWith("image/");

            if (!(isPdf || isDocx || isImage)) {
                throw new IllegalArgumentException("Only PDF, DOCX, and image files are allowed!");
            }

            lostFixedAsset.setFileName(file.getOriginalFilename());
            lostFixedAsset.setFileType(file.getContentType());
            lostFixedAsset.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }
        return lostFixedAsset;
    }
}


