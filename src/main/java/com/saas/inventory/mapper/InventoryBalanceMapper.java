package com.saas.inventory.mapper;

import com.saas.inventory.dto.request.InventoryBalance.InventoryBalanceRequest;
import com.saas.inventory.dto.response.InventoryBalance.InventoryBalanceItemResponse;
import com.saas.inventory.dto.response.InventoryBalance.InventoryBalanceResponse;
import com.saas.inventory.model.InventoryBalance.InventoryBalance;
import com.saas.inventory.model.InventoryBalance.InventoryBalanceItem;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class InventoryBalanceMapper {

    public InventoryBalance mapToEntity(UUID tenantId, InventoryBalanceRequest request, InventoryCount count) {
        InventoryBalance inventoryBalance = new InventoryBalance();

        inventoryBalance.setTenantId(tenantId);
        inventoryBalance.setInventoryCount(count);
        inventoryBalance.setStoreType(request.getStoreType());

        if (request.getInventoryBalanceItemRequest() != null) {
            List<InventoryBalanceItem> details = request.getInventoryBalanceItemRequest().stream().map(itemRequest -> {
                InventoryBalanceItem item = new InventoryBalanceItem();

                item.setItemId(itemRequest.getItemId());
                item.setBinBalance(itemRequest.getBinBalance());
                item.setQuantity(itemRequest.getQuantity());
                item.setDifference(calculateDifference(itemRequest.getQuantity(), itemRequest.getBinBalance()));
                item.setRemark(itemRequest.getRemark());
                item.setInventoryBalance(inventoryBalance);

                return item;
            }).collect(Collectors.toList());

            inventoryBalance.setInventoryBalanceItem(details);
        }

        return inventoryBalance;
    }

    private BigDecimal calculateDifference(BigDecimal quantity, BigDecimal binBalance) {
        BigDecimal qty = quantity != null ? quantity : BigDecimal.ZERO;
        BigDecimal bin = binBalance != null ? binBalance : BigDecimal.ZERO;
        return qty.subtract(bin);
    }


    public InventoryBalanceResponse mapToResponse(InventoryBalance inventoryBalance){
        InventoryBalanceResponse response = new InventoryBalanceResponse();

        response.setId(inventoryBalance.getId());
        response.setTenantId(inventoryBalance.getTenantId());
        response.setInventoryCountId(inventoryBalance.getInventoryCount().getId());
        response.setStoreType(inventoryBalance.getStoreType());
        response.setCreatedAt(inventoryBalance.getCreatedAt());
        response.setUpdatedAt(inventoryBalance.getUpdatedAt());
        response.setCreatedBy(inventoryBalance.getCreatedBy());
        response.setUpdatedBy(inventoryBalance.getUpdatedBy());

        List<InventoryBalanceItemResponse> itemResponse = inventoryBalance.getInventoryBalanceItem().stream().map(item -> {
            InventoryBalanceItemResponse balanceItemResponse = new InventoryBalanceItemResponse();
            balanceItemResponse.setItemId(item.getItemId());
            balanceItemResponse.setQuantity(item.getQuantity());
            balanceItemResponse.setBinBalance(item.getBinBalance());
            balanceItemResponse.setDifference(item.getDifference());
            balanceItemResponse.setRemark(item.getRemark());
            return balanceItemResponse;
        }).collect(Collectors.toList());

        response.setInventoryBalanceItemResponse(itemResponse);

        return response;
    }

    public InventoryBalance mapUpdateRequest(InventoryBalance existing,
                                                              InventoryBalanceRequest request,
                                                              InventoryCount inventoryCount) {

        if (inventoryCount != null) {
            existing.setInventoryCount(inventoryCount);
        }

        if (request.getStoreType() != null) {
            existing.setStoreType(request.getStoreType());
        }

        if (request.getInventoryBalanceItemRequest() != null) {
            List<InventoryBalanceItem> updatedItems = request.getInventoryBalanceItemRequest().stream().map(itemRequest -> {
                InventoryBalanceItem item = new InventoryBalanceItem();
                item.setItemId(itemRequest.getItemId());
                item.setBinBalance(itemRequest.getBinBalance());
                item.setQuantity(itemRequest.getQuantity());
                item.setDifference(calculateDifference(itemRequest.getQuantity(), itemRequest.getBinBalance()));
                item.setRemark(itemRequest.getRemark());
                item.setInventoryBalance(existing);
                return item;
            }).toList();

            existing.getInventoryBalanceItem().clear();
            existing.getInventoryBalanceItem().addAll(updatedItems);
        }

        return existing;
    }


}


