package com.saas.inventory.service;

import com.saas.inventory.dto.request.FixedAssetTransfer.FixedAssetTransferRequest;
import com.saas.inventory.dto.response.FixedAssetTransfer.FixedAssetTransferResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.FixedAssetTransferMapper;
import com.saas.inventory.model.FixedAssetTransfer.FixedAssetTransfer;
import com.saas.inventory.repository.FixedAssetTransfer.FixedAssetTransferDetailRepository;
import com.saas.inventory.repository.FixedAssetTransfer.FixedAssetTransferRepository;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FixedAssetTransferService {

    private final FixedAssetTransferRepository fixedAssetTransferRepository;
    private final FixedAssetTransferDetailRepository fixedAssetTransferDetailRepository;
    private final FixedAssetTransferMapper fixedAssetTransferMapper;
    private final ValidationUtil validationUtil;

    public String generateFixedAssetTransferNumber(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = fixedAssetTransferRepository.findRecentTransferNumbers(tenantId, currentYear);
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
        return String.format("FixedAssetTransferNO-%03d/%d", nextNumber, currentYear);
    }

    @Transactional
    public FixedAssetTransferResponse addFixedAssetTransfer(UUID tenantId, FixedAssetTransferRequest fixedAssetTransferRequest) {

        // Map Dto to entity
        FixedAssetTransfer transferEntity= fixedAssetTransferMapper.toEntity(tenantId,fixedAssetTransferRequest);

        FixedAssetTransfer savedEntity = fixedAssetTransferRepository.save(transferEntity);

        return fixedAssetTransferMapper.toResponse(savedEntity);

    }

    /*
      Get All Fixed ASSET Transfer
     */
    public Page<FixedAssetTransferResponse> getAllFixedAssetTransfer(UUID tenantId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());

        return fixedAssetTransferRepository.findByTenantId(tenantId,pageable)
                .map(fixedAssetTransferMapper::toResponse);
    }

     /*
       Get FixedAssetTransfer By transfer number
      */
     public FixedAssetTransferResponse getFixedAssetTransferByTransferNumber(UUID tenantId, String transferNumber) {
         FixedAssetTransfer fixedAssetTransfer = fixedAssetTransferRepository
                 .findByTenantIdAndTransferNo(tenantId, transferNumber)
                 .filter(fa->fa.getTenantId().equals(tenantId))
                 .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset Transfer not found with transfer number: " + transferNumber));

         return fixedAssetTransferMapper.toResponse(fixedAssetTransfer);
     }

     /*
      Get Fixed Asset Transfer By ID
      */

    public FixedAssetTransferResponse getFixedAssetTransferById(UUID tenantId, UUID id) {
        FixedAssetTransfer fixedAssetTransfer = fixedAssetTransferRepository
                .findById(id)
                .filter(transfer -> transfer.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset Transfer not found with id: " + id));

        return fixedAssetTransferMapper.toResponse(fixedAssetTransfer);
    }

    /*
     Update Fixed Asset Transfer
     */
    @Transactional
    public FixedAssetTransferResponse updateFixedAssetTransfer(UUID tenantId, UUID transferId, FixedAssetTransferRequest request) {
        FixedAssetTransfer existingTransfer = validationUtil.getFixedAssetTransferById(tenantId,transferId);

        existingTransfer=fixedAssetTransferMapper.updateFixedAssetTransfer(existingTransfer,request);

        FixedAssetTransfer updatedTransfer = fixedAssetTransferRepository.save(existingTransfer);

        return fixedAssetTransferMapper.toResponse(updatedTransfer);
    }

    /*
     Delete Fixed Asset Transfer
     */
    public void deleteFixedAssetTransfer(UUID tenantId, UUID id) {
        FixedAssetTransfer fixedAssetTransfer = fixedAssetTransferRepository
                .findById(id)
                .filter(transfer -> transfer.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Fixed Asset Transfer not found with id: " + id));

        fixedAssetTransferRepository.delete(fixedAssetTransfer);
    }

}
