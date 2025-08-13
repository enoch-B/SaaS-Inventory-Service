package com.saas.inventory.mapper;


import com.saas.inventory.dto.request.LostStockItem.LostStockItemRequest;
import com.saas.inventory.dto.response.LostStockItem.LostStockItemDetailResponse;
import com.saas.inventory.dto.response.LostStockItem.LostStockItemResponse;
import com.saas.inventory.model.LostStockItem.LostStockItem;
import com.saas.inventory.model.LostStockItem.LostStockItemDetail;
import com.saas.inventory.utility.FileUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class LostStockItemMapper {

    public LostStockItem mapToEntity(UUID tenantId,
                                     LostStockItemRequest request,
                                     MultipartFile file) throws IOException {

        LostStockItem lostStockItem = new LostStockItem();
        lostStockItem.setTenantId(tenantId);
        lostStockItem.setLostStockItemNo(request.getLostStockItemNo());
        lostStockItem.setRegionId(request.getRegionId());
        lostStockItem.setStoreId(request.getStoreId());
        lostStockItem.setDepartmentId(request.getDepartmentId());
        lostStockItem.setStatus(request.getStatus());
        lostStockItem.setRegistrationDate(request.getRegistrationDate());
        lostStockItem.setCommitteeId(request.getCommitteeId());
        lostStockItem.setCommitteeMembersId(request.getCommitteeMembersId());

        if (request.getLostStockItemDetailRequest() != null) {
            List<LostStockItemDetail> details = request.getLostStockItemDetailRequest().stream().map(detailRequest -> {
                LostStockItemDetail detail = new LostStockItemDetail();
                detail.setItemId(detailRequest.getItemId());
                detail.setDuration(detailRequest.getDuration());
                detail.setDescription(detailRequest.getDescription());
                detail.setQuantity(detailRequest.getQuantity());
                detail.setRemark(detailRequest.getRemark());
                detail.setLostStockItem(lostStockItem);
                return detail;
            }).toList();

            lostStockItem.setLostStockItemDetails(details);
        }

        return getLostStockFile(lostStockItem, file);
    }




    public LostStockItemResponse toResponse(LostStockItem lostStockItem){
        LostStockItemResponse response= new LostStockItemResponse();
        response.setId(lostStockItem.getId());
        response.setTenantId(lostStockItem.getTenantId());
        response.setLostStockItemNo(lostStockItem.getLostStockItemNo());
        response.setRegionId(lostStockItem.getRegionId());
        response.setStoreId(lostStockItem.getStoreId());
        response.setDepartmentId(lostStockItem.getDepartmentId());
        response.setStatus(lostStockItem.getStatus());
        response.setRegistrationDate(lostStockItem.getRegistrationDate());
        response.setCommitteeId(lostStockItem.getCommitteeId());
        response.setCommitteeMembersId(lostStockItem.getCommitteeMembersId());
        response.setCreatedAt(lostStockItem.getCreatedAt());
        response.setUpdatedAt(lostStockItem.getUpdatedAt());
        response.setFileType(lostStockItem.getFileType());
        response.setFileName(lostStockItem.getFileName());
        response.setFileBytes(lostStockItem.getFileBytes());

        if(response.getLostStockItemDetailResponses() != null){
            List<LostStockItemDetailResponse>  details=lostStockItem.getLostStockItemDetails().stream().map(lostStockItemDetail -> {
                LostStockItemDetailResponse detailResponse = new LostStockItemDetailResponse();
                detailResponse.setItemId(lostStockItemDetail.getItemId());
                detailResponse.setDuration(lostStockItemDetail.getDuration());
                detailResponse.setDescription(lostStockItemDetail.getDescription());
                detailResponse.setQuantity(lostStockItemDetail.getQuantity());
                detailResponse.setRemark(lostStockItemDetail.getRemark());

                return detailResponse;

            }).collect(Collectors.toList());

            response.setLostStockItemDetailResponses(details);
        }

        return response;
    }


    public LostStockItem mapUpdateRequest(LostStockItemRequest request,
                                          MultipartFile file,
                                          LostStockItem lostStockItem) throws IOException {

        if (request.getLostStockItemNo() != null) {
            lostStockItem.setLostStockItemNo(request.getLostStockItemNo());
        }

        if (request.getStatus() != null) {
            lostStockItem.setStatus(request.getStatus());
        }

        if (request.getRegistrationDate() != null) {
            lostStockItem.setRegistrationDate(request.getRegistrationDate());
        }

        if (request.getRegionId() != null) {
            lostStockItem.setRegionId(request.getRegionId());
        }

        if (request.getDepartmentId() != null) {
            lostStockItem.setDepartmentId(request.getDepartmentId());
        }

        if (request.getStoreId() != null) {
            lostStockItem.setStoreId(request.getStoreId());
        }

        if (request.getCommitteeId() != null) {
            lostStockItem.setCommitteeId(request.getCommitteeId());
        }

        if (request.getCommitteeMembersId() != null) {
            lostStockItem.setCommitteeMembersId(request.getCommitteeMembersId());
        }

        if (request.getLostStockItemDetailRequest() != null) {
            List<LostStockItemDetail> details = request.getLostStockItemDetailRequest().stream()
                    .map(detailReq -> {
                        LostStockItemDetail detail = new LostStockItemDetail();
                        detail.setItemId(detailReq.getItemId());
                        detail.setQuantity(detailReq.getQuantity());
                        detail.setDescription(detailReq.getDescription());
                        detail.setDuration(detailReq.getDuration());
                        detail.setRemark(detailReq.getRemark());
                        detail.setLostStockItem(lostStockItem);
                        return detail;
                    }).toList();

            lostStockItem.getLostStockItemDetails().clear();
            lostStockItem.getLostStockItemDetails().addAll(details);
        }

        return getLostStockFile(lostStockItem, file);
    }

    private LostStockItem getLostStockFile(LostStockItem lostStockItem, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            boolean isPdf = MediaType.APPLICATION_PDF_VALUE.equals(contentType);
            boolean isDocx = "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType);
            boolean isImage = contentType != null && contentType.startsWith("image/");

            if (!(isPdf || isDocx || isImage)) {
                throw new IllegalArgumentException("Only PDF, DOCX, and image files are allowed!");
            }

            lostStockItem.setFileName(file.getOriginalFilename());
            lostStockItem.setFileType(file.getContentType());
            lostStockItem.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }

        return lostStockItem;
    }
}
