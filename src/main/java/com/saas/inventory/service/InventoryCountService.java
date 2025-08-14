package com.saas.inventory.service;


import com.saas.inventory.dto.request.InventoryCount.InventoryCountRequest;
import com.saas.inventory.dto.response.InventoryCount.InventoryCountResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.InventoryCountMapper;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import com.saas.inventory.repository.InventoryCount.InventoryCountRepository;
import com.saas.inventory.utility.ValidationUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryCountService {
    private final InventoryCountRepository inventoryCountRepository;
    private final ValidationUtil validationUtil;
    private final InventoryCountMapper inventoryCountMapper;

    /**
     * Generates a unique inventory count number per tenant per year.
     */
    public String generateInventoryCountNumber(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = inventoryCountRepository.findRecentInventoryCountNumbers(tenantId, currentYear);
        int nextNumber = 1;

        if (!recentNumbers.isEmpty()) {
            try {
                String latest = recentNumbers.get(0); // e.g., "InventoryNO-003/2025"
                String numberPart = latest.split("-")[1].split("/")[0]; // "003"
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (Exception e) {
                nextNumber = 1;
            }
        }

        return String.format("InventoryNO-%03d/%d", nextNumber, currentYear);
    }

    /**
     * Creates and saves an InventoryCount with its associated detail items.
     */

    @Transactional
    public InventoryCountResponse createInventoryCount(UUID tenantId, InventoryCountRequest request) {

        InventoryCount entity = inventoryCountMapper.toEntity(tenantId,request);

        InventoryCount savedCount = inventoryCountRepository.save(entity);

        return inventoryCountMapper.toResponse(savedCount);
    }

    /**
     * Get a single inventory count by ID (mapped to response DTO).
     */
    public InventoryCountResponse getInventoryCountById(UUID tenantId, UUID id) {
        InventoryCount count = inventoryCountRepository.findById(id)
                .filter(ic -> ic.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("InventoryCount not found or tenant mismatch"));

        return inventoryCountMapper.toResponse(count);
    }

    /**
     * Get paginated inventory counts for a tenant.
     */
    public Page<InventoryCountResponse> getPaginatedInventoryCounts(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return inventoryCountRepository.findByTenantId(tenantId, pageable)
                .map(inventoryCountMapper::toResponse);
    }

    /*
      Get Inventory Count BY unique Count Number
     */

    public InventoryCountResponse getInventoryCountByCountNo(UUID tenantId, String inventoryCountNumber) {
        InventoryCount inventoryCount=inventoryCountRepository
                .findByTenantIdAndInventoryCountNumber(tenantId,inventoryCountNumber)
                .filter(ic->ic.getTenantId().equals(tenantId))
                .orElseThrow(()->new ResourceNotFoundException("Resource Not Found or Tenant Mismatch"));
        return inventoryCountMapper.toResponse(inventoryCount);

    }
   /*
     Update Inventory Count
    */
    public InventoryCountResponse updateInventoryCount(UUID tenantId, UUID id, @Valid InventoryCountRequest request) {
        InventoryCount existingCount= validationUtil.getInventoryCountById(tenantId,id);

        existingCount= inventoryCountMapper.mapUpdateRequest(tenantId,existingCount,request);

        InventoryCount updatedCount= inventoryCountRepository.save(existingCount);

        return inventoryCountMapper.toResponse(updatedCount);
    }

    /**
     * Delete an inventory count by ID and tenant (hard delete).
     */
    public void deleteInventoryCount(UUID tenantId, UUID id) {
        InventoryCount count = inventoryCountRepository.findById(id)
                .filter(ic -> ic.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("InventoryCount not found or tenant mismatch"));

        inventoryCountRepository.delete(count);
    }
}
