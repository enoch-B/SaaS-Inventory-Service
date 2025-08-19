package com.saas.inventory.service;

import com.saas.inventory.dto.request.InventoryBalance.InventoryBalanceRequest;
import com.saas.inventory.dto.response.InventoryBalance.InventoryBalanceResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.InventoryBalanceMapper;
import com.saas.inventory.model.InventoryBalance.InventoryBalance;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import com.saas.inventory.repository.InventoryBalance.InventoryBalanceRepository;
import com.saas.inventory.repository.InventoryCount.InventoryCountRepository;
import com.saas.inventory.utility.ValidationUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryBalanceService {
    private final InventoryBalanceRepository inventoryBalanceRepository;
    private final InventoryBalanceMapper inventoryBalanceMapper;
    private final InventoryCountRepository inventoryCountRepository;
    private final ValidationUtil validationUtil;

    public InventoryBalanceResponse createInventoryBalance(UUID tenantId, InventoryBalanceRequest request) {

        InventoryCount inventoryCount = inventoryCountRepository.findById(request.getInventoryCountId())
                .orElseThrow(() -> new RuntimeException("Inventory Count not found"));

        InventoryBalance balance = inventoryBalanceMapper.mapToEntity(tenantId,request,inventoryCount);
        InventoryBalance saved = inventoryBalanceRepository.save(balance);

        return inventoryBalanceMapper.mapToResponse(saved);
    }

    public Page<InventoryBalanceResponse> getAllInventoryBalance(UUID tenantId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());

        return inventoryBalanceRepository.findByTenantId(tenantId,pageable)
                .map(inventoryBalanceMapper::mapToResponse);

    }

    public InventoryBalanceResponse getInventoryBalanceById(UUID tenantId, UUID id) {
        InventoryBalance inventoryBalance=inventoryBalanceRepository.findById(id)
                .filter(ib->ib.getTenantId().equals(tenantId))
                .orElseThrow(()->new ResourceNotFoundException("Item Not Found Or Tenant Mismatch" +id));

        return  inventoryBalanceMapper.mapToResponse(inventoryBalance);
    }

    public void deleteInventoryBalance(UUID tenantId, UUID id) {
        InventoryBalance inventoryBalance=inventoryBalanceRepository.findById(id)
                .filter(ib->ib.getTenantId().equals(tenantId))
                .orElseThrow(()->new RuntimeException("Item Not Found Or Tenant Mismatch"));

        inventoryBalanceRepository.delete(inventoryBalance);
    }


    public InventoryBalanceResponse updateInventoryBalance(UUID tenantId, UUID id,InventoryBalanceRequest request) {

        InventoryCount inventoryCount = inventoryCountRepository.findById(request.getInventoryCountId())
                .orElseThrow(() -> new RuntimeException("Inventory Count not found"));

        InventoryBalance inventoryBalance=validationUtil.getInventoryBalanceById(tenantId,id);

        inventoryBalance=inventoryBalanceMapper.mapUpdateRequest(tenantId,inventoryBalance,request,inventoryCount);

        InventoryBalance updated=inventoryBalanceRepository.save(inventoryBalance);

        return inventoryBalanceMapper.mapToResponse(updated);
    }

}
