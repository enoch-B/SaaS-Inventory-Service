package com.saas.inventory.service;

import com.saas.inventory.dto.request.FixedAssetReturn.FixedAssetReturnRequest;
import com.saas.inventory.dto.response.FixedAssetReturn.FixedAssetReturnResponse;
import com.saas.inventory.mapper.FixedAssetReturnMapper;
import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturn;
import com.saas.inventory.repository.FixedAssetReturn.FixedAssetReturnRepository;
import com.saas.inventory.utility.ValidationUtil;
import jakarta.transaction.Transactional;
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
public class FixedAssetReturnService {
    private final FixedAssetReturnRepository fixedAssetReturnRepository;
    private  final FixedAssetReturnMapper fixedAssetReturnMapper;
    private final ValidationUtil validationUtil;


      /*
        Generate FixedAssetReturnNumber format; FixedAssetReturnNo-001/2025
       */
    public String generateFixedAssetTransReturnNumber(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = fixedAssetReturnRepository.findRecentReturnNumbers(tenantId, currentYear);
        int nextNumber = 1;

        if (!recentNumbers.isEmpty()) {
            try {
                String latest = recentNumbers.get(0);
                String numberPart = latest.split("-")[1].split("/")[0];
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (Exception e) {
                nextNumber = 1;
            }
        }
        return String.format("FixedAssetReturnNO-%03d/%d", nextNumber, currentYear);
    }


     /*
      Add Fixed Asset Return
      */

     @Transactional
    public FixedAssetReturnResponse addFixedAssetReturn(UUID tenantId, FixedAssetReturnRequest request) {

        FixedAssetReturn entity = fixedAssetReturnMapper.toEntity(tenantId, request);

        FixedAssetReturn savedEntity = fixedAssetReturnRepository.save(entity);

        return fixedAssetReturnMapper.toResponse(savedEntity);
    }


     /*
       Get  All Fixed Asset Transfer
      */

    public Page<FixedAssetReturnResponse> getAllFixedAssetReturns(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return fixedAssetReturnRepository.findByTenantId(tenantId, pageable)
                .map(fixedAssetReturnMapper::toResponse);
    }

    /*
      Get Fixed Asset Return By ID
     */
    public FixedAssetReturnResponse getFixedAssetReturnById(UUID tenantId, UUID id) {
        FixedAssetReturn fixedAssetReturn = fixedAssetReturnRepository.findById(id)
                .filter(far -> far.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Fixed Asset Return Not Found or Tenant Mismatch"));
        return fixedAssetReturnMapper.toResponse(fixedAssetReturn);
    }

    /*
       Delete Fixed Asset Return
     */
    public void deleteFixedAssetReturn(UUID tenantId, UUID id) {
        FixedAssetReturn fixedAssetReturn = fixedAssetReturnRepository.findById(id)
                .filter(far -> far.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Fixed Asset Return Not Found or Tenant Mismatch"));

        fixedAssetReturnRepository.delete(fixedAssetReturn);

    }

    /*
     update fixed asset return
     */
    public FixedAssetReturnResponse updateFixedAssetReturn(UUID tenantId, UUID returnId, FixedAssetReturnRequest request) {

        FixedAssetReturn existingReturn = validationUtil.getFixedAssetReturnById(tenantId, returnId);
        // Update fields from request

        existingReturn= fixedAssetReturnMapper.updateFixedAssetReturn(tenantId,existingReturn, request);

        // Save updated entity
        FixedAssetReturn updatedEntity = fixedAssetReturnRepository.save(existingReturn);

        // Convert to response
        return fixedAssetReturnMapper.toResponse(updatedEntity);
    }
}
