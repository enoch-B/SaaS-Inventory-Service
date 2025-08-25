package com.saas.inventory.controller;

import com.saas.inventory.dto.request.FixedAssetDisposal.FixedAssetDisposalRequest;
import com.saas.inventory.dto.response.FixedAssetDisposal.FixedAssetDisposalResponse;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.service.FixedAssetDisposalService;
import com.saas.inventory.utility.PermissionEvaluator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/fixed-asset-disposal/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Fixed Asset Disposal")
public class FixedAssetDisposalController {
   private final FixedAssetDisposalService fixedAssetDisposalService;
   private final PermissionEvaluator permissionEvaluator;


    /* Endpoint to pre-fetch the next return number for the UI.
     */
    @GetMapping("/next-FAD-number")
    public Map<String, String> getNextFADNumber(@PathVariable UUID tenantId) {
        String nextNumber = fixedAssetDisposalService.generateFA_DisposalNumber(tenantId);
        return Map.of("FADNumber", nextNumber);
    }



    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   public ResponseEntity<?> addFixedAssetDisposal(@PathVariable UUID tenantId,
                                                  @RequestPart("request") @Valid FixedAssetDisposalRequest request, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
         // Check permissions for adding fixed asset disposal
        permissionEvaluator.addFixedAssetDisposalPermission(tenantId);
       FixedAssetDisposalResponse response= fixedAssetDisposalService.addFixedAssetDisposal(tenantId, request, file);
         return new ResponseEntity<>(response, HttpStatus.CREATED);
   }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateFixedAssetDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
             @PathVariable UUID id,
            @RequestPart("request") @Valid FixedAssetDisposalRequest request,
            @Parameter(description = "Optional file attachment") @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        // Check permissions for updating fixed asset disposal
        permissionEvaluator.updateFixedAssetDisposalPermission(tenantId);
        FixedAssetDisposalResponse response = fixedAssetDisposalService.updateFixedAssetDisposal(tenantId, id, request, file);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAllFixedAssetDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // Check permissions for getting all fixed asset disposals
        permissionEvaluator.getAllFixedAssetDisposalPermission(tenantId);
        Page<FixedAssetDisposalResponse> response = fixedAssetDisposalService.getAllFixedAssetDisposal(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFixedAssetDisposalById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Disposal ID") @PathVariable UUID id
    ) {
        // Check permissions for getting fixed asset disposal by ID
        permissionEvaluator.getFixedAssetDisposalByIdPermission(tenantId);

        FixedAssetDisposalResponse response = fixedAssetDisposalService.getFixedAssetDisposalById(tenantId, id);
        return ResponseEntity.ok(response);

    }
    @GetMapping(value = "/download-document/{fixedAssetDisposalId}")
    public ResponseEntity<?> downloadFixedAssetDisposalFileById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Disposal ID") @PathVariable UUID fixedAssetDisposalId
    ) {

        permissionEvaluator.downloadFixedAssetDisposalFilePermission(tenantId);
        FixedAssetDisposalResponse response = fixedAssetDisposalService.getFixedAssetDisposalById(tenantId, fixedAssetDisposalId);
        if (response.getFileType() == null || response.getFileType().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        MediaType mediaType = MediaType.valueOf(response.getFileType());

        byte[] FileBytes = fixedAssetDisposalService.getFixedAssetDisposalFileById(tenantId, fixedAssetDisposalId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + response.getFileName() + "\"")
                .contentType(mediaType)
                .body(FileBytes);
    }





    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFixedAssetDisposal(@PathVariable UUID tenantId,
                                                      @PathVariable UUID id) {
        // Check permissions for deleting fixed asset disposal
        permissionEvaluator.deleteFixedAssetDisposalPermission(tenantId);
        fixedAssetDisposalService.deleteFixedAssetDisposal(tenantId, id);
        return ResponseEntity.noContent().build();
    }

}
