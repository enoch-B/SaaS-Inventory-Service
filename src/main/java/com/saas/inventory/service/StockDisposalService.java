package com.saas.inventory.service;

import com.saas.inventory.dto.request.StockDisposal.StockDisposalRequest;
import com.saas.inventory.dto.response.StockDisposal.StockDisposalResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.StockDisposalMapper;
import com.saas.inventory.model.StockDisposal.StockDisposal;
import com.saas.inventory.repository.StockDisposal.StockDisposalRepository;
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
public class StockDisposalService {
    private final StockDisposalRepository stockDisposalRepository;
    private final StockDisposalMapper stockDisposalMapper;
    private final ValidationUtil validationUtil;


    /*
   // Format: SDNo-001/2025
 */
    public String generateDisposalNo(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = stockDisposalRepository.findRecentDisposalNo(tenantId, currentYear);
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
        return String.format("SDNo-%04d/%d", nextNumber, currentYear);
    }


    public StockDisposalResponse addStockDisposal(UUID tenantId, StockDisposalRequest request, MultipartFile file) throws IOException {

        StockDisposal stockDisposal= stockDisposalMapper.toEntity(tenantId,request,file);


        StockDisposal savedEntity = stockDisposalRepository.save(stockDisposal);

        return stockDisposalMapper.toResponse(savedEntity);

    }

    public StockDisposalResponse updateStockDisposal(UUID tenantId, UUID id, @Valid StockDisposalRequest request, MultipartFile file) throws IOException {


        StockDisposal existingStockDisposal = validationUtil.getStockDisposalById(id, tenantId);

        existingStockDisposal=stockDisposalMapper.mapUpdateRequest(tenantId, existingStockDisposal, request, file);


        StockDisposal updatedEntity = stockDisposalRepository.save(existingStockDisposal);

        return stockDisposalMapper.toResponse(updatedEntity);
    }


    public Page<StockDisposalResponse> getAllStockDisposal(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return stockDisposalRepository.findByTenantId(tenantId,pageable)
                .map(stockDisposalMapper::toResponse);
    }

    public StockDisposalResponse getStockDisposalById(UUID tenantId,UUID id) {
        StockDisposal stockDisposal = stockDisposalRepository.findById(id)
                .filter(sd->sd.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Stock Disposal not found with ID: " + id));

        return stockDisposalMapper.toResponse(stockDisposal);
    }

    public void deleteStockDisposal(UUID id, UUID uuid) {
        StockDisposal existing = stockDisposalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Disposal not found with ID: " + id));
        stockDisposalRepository.delete(existing);
    }

    public StockDisposalResponse getStockDisposalBySDNumber(UUID tenantId, String disposalNumber) {
        StockDisposal stockDisposal = stockDisposalRepository.findByTenantIdAndDisposalNo(tenantId, disposalNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Stock Disposal not found with Disposal Number: " + disposalNumber));

        return stockDisposalMapper.toResponse(stockDisposal);
    }
}
