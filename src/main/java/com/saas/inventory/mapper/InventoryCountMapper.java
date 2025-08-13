package com.saas.inventory.mapper;

import com.saas.inventory.dto.request.InventoryCount.InventoryCountRequest;
import com.saas.inventory.dto.response.InventoryCount.InventoryCountDetailResponse;
import com.saas.inventory.dto.response.InventoryCount.InventoryCountResponse;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import com.saas.inventory.model.InventoryCountSheet.InventoryDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InventoryCountMapper {


    public InventoryCount toEntity(UUID tenantId, InventoryCountRequest request){
        InventoryCount inventoryCount = new InventoryCount();
        inventoryCount.setTenantId(tenantId);
        inventoryCount.setStoreId(request.getStoreId());
        inventoryCount.setInventoryCountNumber(request.getInventoryCountNumber());
        inventoryCount.setBudgetYearId(request.getBudgetYearId());
        inventoryCount.setCountType(request.getCountType());
        inventoryCount.setStoreType(request.getStoreType());
        inventoryCount.setCommitteeId(request.getCommitteeId());
        inventoryCount.setCommitteeMembersId(request.getCommitteeMembersId());
        inventoryCount.setCountDate(request.getCountDate());
        if (request.getInventoryItems() != null) {
            List<InventoryDetail> details = request.getInventoryItems().stream().map(itemRequest -> {
                InventoryDetail inventoryDetail = new InventoryDetail();
                inventoryDetail.setItemId(itemRequest.getItemId());
                inventoryDetail.setQuantity(itemRequest.getQuantity());
                inventoryDetail.setRemark(itemRequest.getRemark());
                inventoryDetail.setInventoryCount(inventoryCount);
                return inventoryDetail;
            }).collect(Collectors.toList());
            inventoryCount.setInventoryDetails(details);
        }

        return inventoryCount;
    }

    public InventoryCountResponse toResponse(InventoryCount count ){
        InventoryCountResponse response = new InventoryCountResponse();
        response.setId(count.getId());
        response.setTenantId(count.getTenantId());
        response.setInventoryCountNumber(count.getInventoryCountNumber());
        response.setStoreId(count.getStoreId());
        response.setBudgetYearId(count.getBudgetYearId());
        response.setCountType(count.getCountType());
        response.setStoreType(count.getStoreType());
        response.setCommitteeId(count.getCommitteeId());
        response.setCommitteeMembersId(count.getCommitteeMembersId());
        response.setCountDate(count.getCountDate());
        response.setCreatedAt(count.getCreatedAt());
        response.setUpdatedAt(count.getUpdatedAt());
        response.setCreatedBy(count.getCreatedBy());
        response.setUpdatedBy(count.getUpdatedBy());

        if(count.getInventoryDetails() != null) {
            List<InventoryCountDetailResponse> details = count.getInventoryDetails().stream().map(detail ->{
                InventoryCountDetailResponse detailResponse = new InventoryCountDetailResponse();
                detailResponse.setItemId(detail.getItemId());
                detailResponse.setQuantity(detail.getQuantity());
                detailResponse.setRemark(detail.getRemark());

                return detailResponse;
            }).collect(Collectors.toList());
            response.setInventoryDetails(details);
        }
        return response;
    }


    public InventoryCount mapUpdateRequest(InventoryCount inventoryCount,
                                           InventoryCountRequest request) {

        if (request.getStoreId() != null) {
            inventoryCount.setStoreId(request.getStoreId());
        }

        if (request.getBudgetYearId() != null) {
            inventoryCount.setBudgetYearId(request.getBudgetYearId());
        }

        if (request.getCountType() != null) {
            inventoryCount.setCountType(request.getCountType());
        }

        if (request.getStoreType() != null) {
            inventoryCount.setStoreType(request.getStoreType());
        }

        if (request.getCommitteeId() != null) {
            inventoryCount.setCommitteeId(request.getCommitteeId());
        }

        if (request.getCommitteeMembersId() != null) {
            inventoryCount.setCommitteeMembersId(request.getCommitteeMembersId());
        }

        if (request.getCountDate() != null) {
            inventoryCount.setCountDate(request.getCountDate());
        }

        if (request.getInventoryItems() != null) {
            List<InventoryDetail> details = request.getInventoryItems().stream()
                    .map(itemRequest -> {
                        InventoryDetail detail = new InventoryDetail();
                        detail.setItemId(itemRequest.getItemId());
                        detail.setQuantity(itemRequest.getQuantity());
                        detail.setRemark(itemRequest.getRemark());
                        detail.setInventoryCount(inventoryCount);
                        return detail;
                    }).toList();

            inventoryCount.getInventoryDetails().clear();
            inventoryCount.getInventoryDetails().addAll(details);
        }

        return inventoryCount;
    }


}

