package com.saas.inventory.service;


import com.saas.inventory.dto.request.LostStockItem.LostStockItemRequest;
import com.saas.inventory.dto.response.LostStockItem.LostStockItemResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.LostStockItemMapper;
import com.saas.inventory.model.LostStockItem.LostStockItem;
import com.saas.inventory.repository.LostStockItem.LostStockItemRepository;
import com.saas.inventory.utility.ValidationUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LostStockItemService {
    private final LostStockItemRepository lostStockItemRepository;
    private final LostStockItemMapper lostStockItemMapper;
    private final ValidationUtil validationUtil;


    /*
   // Format: StockLostItemNo-001/2025
 */
    public String generateLostStockItemNo(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = lostStockItemRepository.findRecentLostStockItemNo(tenantId, currentYear);
        int nextNumber = 1;

        if (!recentNumbers.isEmpty()) {
            try {
                String latest = recentNumbers.get(0); // e.g., "StockLostItemNo-003/2025"
                String numberPart = latest.split("-")[1].split("/")[0]; // "003"
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (Exception e) {
                nextNumber = 1;
            }
        }
        return String.format("StockLostItemNo-%03d/%d", nextNumber, currentYear);
    }



    /*
       Add/Register New Lost Stock Item
    */
    public LostStockItemResponse addLostStockItem(UUID tenantId, @Valid LostStockItemRequest request, MultipartFile file) throws IOException {
        LostStockItem lostStockItem=lostStockItemMapper.mapToEntity(tenantId,request,file);

        LostStockItem savedItem= lostStockItemRepository.save(lostStockItem);

        return lostStockItemMapper.toResponse(savedItem);
    }

    /*
   Get all lost stock item paginated
 */
    public Page<LostStockItemResponse> getAllLostStockItem(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return lostStockItemRepository.findByTenantId(tenantId,pageable)
                .map(lostStockItemMapper::toResponse);
    }


    public LostStockItemResponse getLostStockItemById(UUID tenantId, UUID id) {
        LostStockItem lostStockItem = lostStockItemRepository.findById(id)
                .filter(si -> si.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("LostStock Item Not Found or tenant Mismatch"));

        return lostStockItemMapper.toResponse(lostStockItem);
    }

    /*
      delete lost stock item
  */
    public void deleteLostStockItem(UUID tenantId, UUID id) {

        LostStockItem lostStockItem= lostStockItemRepository.findById(id)
                .filter(si -> si.getTenantId().equals(tenantId))
                .orElseThrow( () -> new RuntimeException("Item Not found or tenant mismatch"));

        lostStockItemRepository.delete(lostStockItem);
    }


    public LostStockItemResponse updateLostStockItem(UUID tenantId, UUID id,LostStockItemRequest request, MultipartFile file) throws IOException {
        LostStockItem existingItem=validationUtil.getLostStockItemById(tenantId,id);

        existingItem= lostStockItemMapper.mapUpdateRequest(request, file, existingItem);

        LostStockItem updated=lostStockItemRepository.save(existingItem);

        return lostStockItemMapper.toResponse(updated);
    }

    public LostStockItemResponse getLostStockItemByLSNo(UUID tenantId, String lostStockItemNo) {
        LostStockItem lostStockItem=lostStockItemRepository
                .findByTenantIdAndLostStockItemNo(tenantId,lostStockItemNo)
                .orElseThrow(()->new ResourceNotFoundException("Item Not Found "));

        return lostStockItemMapper.toResponse(lostStockItem);


    }
}
