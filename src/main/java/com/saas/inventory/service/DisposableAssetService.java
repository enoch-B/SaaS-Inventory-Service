package com.saas.inventory.service;

import com.saas.inventory.dto.request.DisposableAsset.DisposableAssetRequest;
import com.saas.inventory.dto.response.DiposalCollection.DisposableAssetResponse;
import com.saas.inventory.exception.ForeignKeyException;
import com.saas.inventory.mapper.DisposableAssetMapper;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.model.DisposalCollection.DisposableFixedAssetDetail;
import com.saas.inventory.repository.DispoalCollection.DisposableAssetRepository;
import com.saas.inventory.repository.DispoalCollection.DisposableFixedAssetDetailRepository;
import com.saas.inventory.repository.FixedAssetDisposal.FixedAssetDisposalRepository;
import com.saas.inventory.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DisposableAssetService {

    private final DisposableAssetRepository disposableAssetRepository;
    private final DisposableFixedAssetDetailRepository disposableFixedAssetDetailRepository;
    private final FixedAssetDisposalRepository fixedAssetDisposalRepository;
    private final DisposableAssetMapper disposableAssetMapper;
    private final ValidationUtil validationUtil;


    /**
     * Generates a unique FA Disposal number per tenant per year.
     */
    public String generateFADNumber(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = disposableAssetRepository.findRecentFADNumbers(tenantId, currentYear);
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

        return String.format("FADisposalNo-%03d/%d", nextNumber, currentYear);
    }

    /**
     * Creates and saves a Disposable Asset with its associated detail items.
     *
     *
     */
    public DisposableAssetResponse addDisposalCollection(UUID tenantId, DisposableAssetRequest disposableAssetRequest) throws IOException {
        String drNumber=generateFADNumber(tenantId);
        disposableAssetRequest.setDrNo(drNumber);

        DisposableAsset disposableAsset = disposableAssetMapper.toEntity(tenantId,disposableAssetRequest);

        //Link each detail to the parent
        if(disposableAsset.getDisposableAssetDetail() !=null){
            for(DisposableFixedAssetDetail detail: disposableAsset.getDisposableAssetDetail()){
                detail.setDisposableAsset(disposableAsset);
            }

        }

        //save parent and cascade children

        DisposableAsset savedAsset = disposableAssetRepository.save(disposableAsset);

        return disposableAssetMapper.toResponse(savedAsset);
    }

       /*
        GET all Disposal COLLECTION paginated
     */

    public Page<DisposableAssetResponse> getAllDisposalCollection(UUID tenantId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return disposableAssetRepository.findByTenantId(tenantId,pageable).map(disposableAssetMapper::toResponse);
    }

    /*
   Get Disposable asset by id
  */
    public DisposableAssetResponse getDisposalCollectionById(UUID tenantId, UUID id) {
        DisposableAsset disposableAsset= disposableAssetRepository.findById(id)
                .filter(da -> da.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Disposal Collection Not Found or tenant Mismatch"));

        return disposableAssetMapper.toResponse(disposableAsset);
    }


    public DisposableAssetResponse getDisposalCollectionByDrNo(UUID tenantId, String drNo) {
         validationUtil.validateDrNo(drNo);

        DisposableAsset disposableAsset = disposableAssetRepository.findByDrNoAndTenantId(drNo, tenantId)
                .orElseThrow(() -> new RuntimeException("Disposal Collection Not Found or tenant Mismatch"));

        return disposableAssetMapper.toResponse(disposableAsset);
    }

    public void deleteDisposableAsset(UUID tenantId, UUID id) {
        DisposableAsset disposableAsset = disposableAssetRepository.findById(id)
                .filter(da -> da.getTenantId().equals(tenantId))
                .orElseThrow( () -> new RuntimeException("Disposable Asset Not Found Or tenant mismatch "));

        if (fixedAssetDisposalRepository.existsByDisposableAssetId(id)) {
            throw new ForeignKeyException("This disposable asset is already used in a disposal record and cannot be deleted.");
        }
        disposableAssetRepository.delete(disposableAsset);
    }


    public DisposableAssetResponse updateDisposableAsset(UUID tenantId, UUID id, DisposableAssetRequest disposableAssetRequest) {
        DisposableAsset existing = disposableAssetRepository.findById(id)
                .filter(da -> da.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Disposable Asset Not Found by this id: " + id));

        // Update the existing entity with the new request data
        disposableAssetMapper.updateDisposableAssetFromRequest(tenantId,disposableAssetRequest, existing);

        // Save the updated entity
        DisposableAsset updatedAsset = disposableAssetRepository.save(existing);

        return disposableAssetMapper.toResponse(updatedAsset);
    }
}
