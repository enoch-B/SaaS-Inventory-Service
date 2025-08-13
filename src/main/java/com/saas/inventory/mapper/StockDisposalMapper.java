package com.saas.inventory.mapper;

import com.saas.inventory.dto.clientDto.FixedAssetDto;
import com.saas.inventory.dto.clientDto.StoreDto;
import com.saas.inventory.dto.request.StockDisposal.StockDisposalRequest;
import com.saas.inventory.dto.response.StockDisposal.StockDisposalDetailResponse;
import com.saas.inventory.dto.response.StockDisposal.StockDisposalResponse;
import com.saas.inventory.model.StockDisposal.StockDisposal;
import com.saas.inventory.model.StockDisposal.StockDisposalDetail;
import com.saas.inventory.utility.FileUtil;
import com.saas.inventory.utility.ValidationUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StockDisposalMapper {

    private final ValidationUtil validationUtil;

    public StockDisposalMapper(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public StockDisposal toEntity(UUID tenantId, StockDisposalRequest request, MultipartFile file) throws IOException {
        StockDisposal stockDisposal = new StockDisposal();

        StoreDto storeDto=validationUtil.getStoreById(tenantId,request.getStoreId());


        stockDisposal.setTenantId(tenantId);
        stockDisposal.setStoreId(storeDto.getId());
        stockDisposal.setDisposalNo(request.getDisposalNo());
        stockDisposal.setDisposalStatus(request.getDisposalStatus());
        stockDisposal.setProposeDate(request.getProposeDate());
        stockDisposal.setApprovedDate(request.getApprovedDate());

        if (request.getStockDisposalDetails() != null) {
            List<StockDisposalDetail> details = request.getStockDisposalDetails().stream().map(detailRequest -> {
                StockDisposalDetail detail = new StockDisposalDetail();

                FixedAssetDto item=validationUtil.getItemById(tenantId,detailRequest.getItemId());

                detail.setItemId(item.getId());
                detail.setDisposalMethod(detailRequest.getDisposalMethod());
                detail.setDescription(detailRequest.getDescription());
                detail.setSellingPrice(detailRequest.getSellingPrice());
                detail.setExpirationDate(detailRequest.getExpirationDate());
                detail.setStockDisposal(stockDisposal);
                return detail;
            }).collect(Collectors.toList());

            stockDisposal.setStockDisposalDetails(details);
        }

        return getStockDisposalFile(stockDisposal,file);
    }


    private StockDisposal getStockDisposalFile(StockDisposal stockDisposal, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            boolean isPdf = MediaType.APPLICATION_PDF_VALUE.equals(contentType);
            boolean isDocx = "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType);
            boolean isImage = contentType != null && contentType.startsWith("image/");

            if (!(isPdf || isDocx || isImage)) {
                throw new IllegalArgumentException("Only PDF, DOCX, and image files are allowed!");
            }

            stockDisposal.setFileName(file.getOriginalFilename());
            stockDisposal.setFileType(file.getContentType());
            stockDisposal.setFileBytes(FileUtil.compressFile(file.getBytes()));
        }

        return stockDisposal;
    }


    public StockDisposalResponse toResponse(StockDisposal entity) {
        StockDisposalResponse response = new StockDisposalResponse();
        response.setId(entity.getId());
        response.setTenantId(entity.getTenantId());
        response.setStoreId(entity.getStoreId());
        response.setDisposalNo(entity.getDisposalNo());
        response.setDisposalStatus(entity.getDisposalStatus());
        response.setProposeDate(entity.getProposeDate());
        response.setApprovedDate(entity.getApprovedDate());
        response.setFileName(entity.getFileName());
        response.setFileType(entity.getFileType());
        response.setFileBytes(entity.getFileBytes());
        response.setCreatedAt(entity.getCreatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setUpdatedBy(entity.getUpdatedBy());

        if (entity.getStockDisposalDetails() != null) {
            List<StockDisposalDetailResponse> details = entity.getStockDisposalDetails().stream().map(detail -> {
                StockDisposalDetailResponse detailResponse = new StockDisposalDetailResponse();
                detailResponse.setItemId(detail.getItemId());
                detailResponse.setDisposalMethod(detail.getDisposalMethod());
                detailResponse.setDescription(detail.getDescription());
                detailResponse.setSellingPrice(detail.getSellingPrice());
                detailResponse.setExpirationDate(detail.getExpirationDate());
                return detailResponse;
            }).collect(Collectors.toList());

            response.setStockDisposalDetails(details);
        }

        return response;
    }

    public StockDisposal mapUpdateRequest(UUID tenantId,StockDisposal existing, StockDisposalRequest request,MultipartFile file) throws IOException {

         StoreDto storeDto=validationUtil.getStoreById(tenantId,request.getStoreId());

        if (request.getStoreId() != null) {
            existing.setStoreId(storeDto.getId());
        }

        if (request.getDisposalStatus() != null) {
            existing.setDisposalStatus(request.getDisposalStatus());
        }

        if (request.getProposeDate() != null) {
            existing.setProposeDate(request.getProposeDate());
        }

        if (request.getApprovedDate() != null) {
            existing.setApprovedDate(request.getApprovedDate());
        }

        if (request.getFileName() != null) {
            existing.setFileName(request.getFileName());
        }

        if (request.getFileType() != null) {
            existing.setFileType(request.getFileType());
        }

        if (request.getFileBytes() != null) {
            existing.setFileBytes(request.getFileBytes());
        }

        if (request.getStockDisposalDetails() != null) {
            List<StockDisposalDetail> details = request.getStockDisposalDetails().stream()
                    .map(detailRequest -> {
                        StockDisposalDetail detail = new StockDisposalDetail();

                        FixedAssetDto item = validationUtil.getItemById(tenantId, detailRequest.getItemId());

                        detail.setItemId(item.getId());
                        detail.setDisposalMethod(detailRequest.getDisposalMethod());
                        detail.setDescription(detailRequest.getDescription());
                        detail.setSellingPrice(detailRequest.getSellingPrice());
                        detail.setExpirationDate(detailRequest.getExpirationDate());
                        detail.setStockDisposal(existing);
                        return detail;
                    }).toList();

            existing.getStockDisposalDetails().clear();
            existing.getStockDisposalDetails().addAll(details);
        }

        return getStockDisposalFile(existing,file);
    }
}
