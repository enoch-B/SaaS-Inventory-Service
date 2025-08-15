package com.saas.inventory.controller;


import com.saas.inventory.dto.request.DisposableAsset.DisposableAssetRequest;
import com.saas.inventory.dto.response.DiposalCollection.DisposableAssetResponse;
import com.saas.inventory.service.DisposableAssetService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/disposable-assets/{tenantId}")
@RequiredArgsConstructor
@Tag(name="Disposal Collection")
public class DisposableAssetController {

    private final DisposableAssetService disposableAssetService;
    private final PermissionEvaluator permissionEvaluator;

    /* Endpoint to pre-fetch the next return number for the UI.
     */
    @GetMapping("/next-drNo-number")
    public Map<String, String> getNextReturnNumber(@PathVariable UUID tenantId) {
        String nextNumber = disposableAssetService.generateFADNumber(tenantId);
        return Map.of("drNumber", nextNumber);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDisposalCollection(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Valid @RequestBody DisposableAssetRequest disposableAssetRequest) throws IOException {

         permissionEvaluator.addDisposalCollectionPermission(tenantId);

        DisposableAssetResponse response = disposableAssetService.addDisposalCollection(tenantId, disposableAssetRequest);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<DisposableAssetResponse>> getAllDisposalCollection(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        permissionEvaluator.getAllDisposalCollectionPermission(tenantId);
        Page<DisposableAssetResponse> response = disposableAssetService.getAllDisposalCollection(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DisposableAssetResponse> getDisposalCollectionById(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Disposable Asset ID", required = true) @PathVariable UUID id) {

        permissionEvaluator.getDisposalCollectionByIdPermission(tenantId);
        DisposableAssetResponse response = disposableAssetService.getDisposalCollectionById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-drNo")
    public ResponseEntity<DisposableAssetResponse> getDisposalCollectionByDrNo(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Disposal Collection DR Number", required = true) @RequestParam String drNo) {

        permissionEvaluator.getDisposalCollectionByDrNoPermission(tenantId);
        DisposableAssetResponse response = disposableAssetService.getDisposalCollectionByDrNo(tenantId, drNo);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDisposableAsset(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Disposable Asset ID", required = true) @PathVariable UUID id) {
        permissionEvaluator.deleteDisposalCollectionPermission(tenantId);
        disposableAssetService.deleteDisposableAsset(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DisposableAssetResponse> updateDisposableAsset(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Disposable Asset ID", required = true) @PathVariable UUID id,
            @Valid @RequestBody DisposableAssetRequest disposableAssetRequest) {

        permissionEvaluator.updateDisposalCollectionPermission(tenantId);
        DisposableAssetResponse response = disposableAssetService.updateDisposableAsset(tenantId, id, disposableAssetRequest);
        return ResponseEntity.ok(response);
    }




}
