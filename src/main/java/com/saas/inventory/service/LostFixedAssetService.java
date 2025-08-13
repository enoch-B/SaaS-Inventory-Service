package com.saas.inventory.service;

import com.saas.inventory.dto.request.LostFixedAsset.LostFixedAssetRequest;
import com.saas.inventory.dto.response.LostFixedAsset.LostFixedAssetResponse;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.mapper.LostFixedAssetMapper;
import com.saas.inventory.model.LostFixedAsset.LostFixedAsset;
import com.saas.inventory.repository.LostFixedAsset.LostFixedAssetRepository;
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
public class LostFixedAssetService {
     private final LostFixedAssetRepository lostFixedAssetRepository;
     private final LostFixedAssetMapper lostFixedAssetMapper;
     private final ValidationUtil validationUtil;


    public String generateLostItemNo(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = lostFixedAssetRepository.findRecentLostItemNo(tenantId, currentYear);
        int nextNumber = 1;

        if (!recentNumbers.isEmpty()) {
            try {
                String latest = recentNumbers.get(0); // e.g., "DisposalNO-003/2025"
                String numberPart = latest.split("-")[1].split("/")[0]; // "003"
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (Exception e) {
                nextNumber = 1;
            }
        }
        return String.format("LostItemNo-%03d/%d", nextNumber, currentYear);
    }
  /*
    register lost fixed asset
   */

    public LostFixedAssetResponse addLostFixedAsset(UUID tenantId, LostFixedAssetRequest lostFixedAssetRequest, MultipartFile file) throws IOException {
        // Map the DTO to Entity
        LostFixedAsset lostFixedAsset = lostFixedAssetMapper.toEntity(tenantId,lostFixedAssetRequest,file);

        //save the entity to the repository
        LostFixedAsset savedEntity = lostFixedAssetRepository.save(lostFixedAsset);

        return lostFixedAssetMapper.toResponse(savedEntity);

    }
    /*
      GET all lost fixed asset
     */

    public Page<LostFixedAssetResponse> getAllLostFixedAssets(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return lostFixedAssetRepository.findByTenantId(tenantId,pageable)
                .map(lostFixedAssetMapper::toResponse);
    }

    /*
      Retrieve Lost Fixed assets by id
     */

    public LostFixedAssetResponse getLostFixedAssetById(UUID tenantId, UUID id) {
        LostFixedAsset lostFixedAsset= lostFixedAssetRepository.findById(id)
                .filter(la->la.getTenantId().equals(tenantId))
                .orElseThrow(()->new ResourceNotFoundException("LostFixed Asset Not Found By this id" + id));

        return lostFixedAssetMapper.toResponse(lostFixedAsset);
    }

    /*
    update the whole entity
   */
    public LostFixedAssetResponse updateLostFixedAsset(UUID tenantId, UUID id,LostFixedAssetRequest request, MultipartFile file) throws IOException {
        LostFixedAsset existingAsset=validationUtil.getLostFixedAssetById(tenantId,id);


        existingAsset=lostFixedAssetMapper.updateLostFixedAsset(existingAsset,request,file);

        LostFixedAsset updated= lostFixedAssetRepository.save(existingAsset);

        return lostFixedAssetMapper.toResponse(updated);

    }

    /*
     Delete lost Fixed Asset
     */
    public void deleteLostFixedAsset(UUID tenantId, UUID id) {
        LostFixedAsset lostFixedAsset=lostFixedAssetRepository.findById(id)
                .filter(la->la.getTenantId().equals(tenantId))
                .orElseThrow(()-> new RuntimeException("Item Not Found or Tenant Mismatch"));

        lostFixedAssetRepository.delete(lostFixedAsset);
    }

    /*
       Get lost fixed asset by unique number
     */

    public LostFixedAssetResponse getLostFixedAssetByLostItemNo(UUID tenantId, String lostItemNo) {
        LostFixedAsset lostFixedAsset= lostFixedAssetRepository
                .findByTenantIdAndLostItemNo(tenantId,lostItemNo)
                .filter(la->la.getTenantId().equals(tenantId))
                .orElseThrow(()-> new ResourceNotFoundException("Item Not Found"));

        return lostFixedAssetMapper.toResponse(lostFixedAsset);
    }
}
