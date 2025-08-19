package com.saas.inventory.service;

import com.saas.inventory.dto.request.FixedAssetDisposal.FixedAssetDisposalRequest;
import com.saas.inventory.dto.response.FixedAssetDisposal.FixedAssetDisposalResponse;
import com.saas.inventory.enums.DisposalStatus;
import com.saas.inventory.mapper.FixedAssetDisposalMapper;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.model.FixedAssetDisposal.FixedAssetDisposal;
import com.saas.inventory.repository.DispoalCollection.DisposableAssetRepository;
import com.saas.inventory.repository.FixedAssetDisposal.FixedAssetDisposalRepository;
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
public class FixedAssetDisposalService {

    private final FixedAssetDisposalRepository fixedAssetDisposalRepository;
    private final DisposableAssetRepository disposableAssetRepository;
    private final FixedAssetDisposalMapper fixedAssetDisposalMapper;
    private final ValidationUtil validationUtil;


    public String generateFA_DisposalNumber(UUID tenantId) {
        int currentYear = LocalDate.now().getYear();
        List<String> recentNumbers = fixedAssetDisposalRepository.findRecentDisposalNumbers(tenantId, currentYear);
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
        return String.format("FixedAssetDisposalNO-%03d/%d", nextNumber, currentYear);
    }

    public FixedAssetDisposalResponse addFixedAssetDisposal(UUID tenantId, FixedAssetDisposalRequest request, MultipartFile file) throws IOException {

        DisposableAsset disposableAsset = validationUtil.getDisposableAssetById(tenantId, request.getDisposableAssetId());


        FixedAssetDisposal entity = fixedAssetDisposalMapper.toEntity(disposableAsset,tenantId, request, file);

        FixedAssetDisposal savedEntity = fixedAssetDisposalRepository.save(entity);

        return fixedAssetDisposalMapper.toResponse(savedEntity);

    }


    public FixedAssetDisposalResponse updateFixedAssetDisposal(UUID tenantId, UUID id, FixedAssetDisposalRequest request, MultipartFile file) throws IOException {
       DisposableAsset disposableAsset=validationUtil.getDisposableAssetById(tenantId, id);

        FixedAssetDisposal existing= validationUtil.getFixedAssetDisposalById(tenantId, id);

        existing=fixedAssetDisposalMapper.updateEntity(tenantId, existing, request,file, disposableAsset);

        FixedAssetDisposal updatedEntity = fixedAssetDisposalRepository.save(existing);

        return fixedAssetDisposalMapper.toResponse(updatedEntity);
    }

    /*
     * get all paginated fixed asset disposal
     */

    public Page<FixedAssetDisposalResponse> getAllFixedAssetDisposal(UUID tenantId, int page, int size) {
        Pageable pageable= PageRequest.of(page,size, Sort.by("createdAt").descending());

        return fixedAssetDisposalRepository.findByTenantId(tenantId,pageable)
                .map(fixedAssetDisposalMapper::toResponse);
    }

    public FixedAssetDisposalResponse getFixedAssetDisposalById(UUID tenantId, UUID id) {
        // Fetch the fixed asset disposal by ID
        FixedAssetDisposal fixedAssetDisposal = fixedAssetDisposalRepository.findById(id)
                .filter(disposal -> disposal.getTenantId().equals(tenantId))
                .orElseThrow(() -> new RuntimeException("Fixed Asset Disposal not found with id: " + id));
        // Map the entity to response DTO
        return fixedAssetDisposalMapper.toResponse(fixedAssetDisposal);
    }


}
