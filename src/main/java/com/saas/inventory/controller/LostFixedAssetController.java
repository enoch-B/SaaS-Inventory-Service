package com.saas.inventory.controller;

import com.saas.inventory.dto.request.LostFixedAsset.LostFixedAssetRequest;
import com.saas.inventory.dto.response.LostFixedAsset.LostFixedAssetResponse;
import com.saas.inventory.model.LostFixedAsset.LostFixedAsset;
import com.saas.inventory.service.LostFixedAssetService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("api/inventory/lost-fixed-asset/{tenantId}")
@Tag(name="Lost Fixed Asset")
@RequiredArgsConstructor
public class LostFixedAssetController {
    private final LostFixedAssetService lostFixedAssetService;
    private final PermissionEvaluator permissionEvaluator;

    /* Endpoint to pre-fetch the next LostItem number for the UI.
     */
    @GetMapping("/next-LostItem-number")
    public Map<String, String> getNextLostItemNumber(@PathVariable UUID tenantId) {

        String nextNumber = lostFixedAssetService.generateLostItemNo(tenantId);
        return Map.of("itemNumber", nextNumber);
    }


    @PostMapping(value="/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addLostFixedAsset(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @RequestPart("request") @Valid LostFixedAssetRequest lostFixedAssetRequest,
            @RequestPart(value = "file", required = false)MultipartFile file)
            throws IOException {

        permissionEvaluator.addLostFixedAssetPermission(tenantId);

        LostFixedAssetResponse response=lostFixedAssetService.addLostFixedAsset(tenantId, lostFixedAssetRequest,file);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

     @GetMapping("/download-file/{lostFixedAssetId}")
    public ResponseEntity<byte[]> downloadFile(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Fixed Asset ID") @PathVariable UUID lostFixedAssetId) throws IOException {

        permissionEvaluator.downloadLostFixedAssetFilePermission(tenantId);

        LostFixedAssetResponse lostFixedAssetResponse = lostFixedAssetService.getLostFixedAssetById(tenantId, lostFixedAssetId);

        if(lostFixedAssetResponse.getFileType()==null || lostFixedAssetResponse.getFileType().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        MediaType mediaType=MediaType.valueOf(lostFixedAssetResponse.getFileType());


        byte[] fileData = lostFixedAssetService.downloadLostFixedAssetFile(tenantId, lostFixedAssetId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + lostFixedAssetResponse.getFileName() + "\"")
                .contentType(mediaType)
                .body(fileData);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<LostFixedAssetResponse>> getAllLostFixedAssets(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

         permissionEvaluator.getAllLostFixedAssetPermission(tenantId);
        Page<LostFixedAssetResponse> response = lostFixedAssetService.getAllLostFixedAssets(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLostFixedAssetById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Fixed Asset ID") @PathVariable UUID id) {

        permissionEvaluator.getLostFixedAssetByIdPermission(tenantId);

        LostFixedAssetResponse response = lostFixedAssetService.getLostFixedAssetById(tenantId, id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-by/{LostItemNo}")
    public ResponseEntity<?>getLFAssetByLostItemNo(@PathVariable UUID tenantId,
                                                   @PathVariable String lostItemNo) throws IOException{

        permissionEvaluator.getByLostItemNoPermission(tenantId);
        LostFixedAssetResponse response=lostFixedAssetService.getLostFixedAssetByLostItemNo(tenantId,lostItemNo);

        return ResponseEntity.ok(response);

    }




    @PutMapping(value="/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateLostFixedAsset(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Fixed Asset ID") @PathVariable UUID id,
            @RequestPart("request") @Valid LostFixedAssetRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file
            ) throws  IOException{

        permissionEvaluator.updateLostFixedAssetTransferPermission(tenantId);

        LostFixedAssetResponse updated = lostFixedAssetService.updateLostFixedAsset(tenantId, id, request,file);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLostFixedAsset(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Fixed Asset ID") @PathVariable UUID id) {
        permissionEvaluator.deleteLostFixedAssetPermission(tenantId);

        lostFixedAssetService.deleteLostFixedAsset(tenantId, id);

        return ResponseEntity.ok("Deleted Successfully");
    }





}
