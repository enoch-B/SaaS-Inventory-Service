package com.saas.inventory.controller;


import com.saas.inventory.dto.request.FixedAssetTransfer.FixedAssetTransferRequest;
import com.saas.inventory.dto.response.FixedAssetTransfer.FixedAssetTransferResponse;
import com.saas.inventory.service.FixedAssetTransferService;
import com.saas.inventory.utility.PermissionEvaluator;

import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/fixed-asset-transfer/{tenantId}")
@Tag(name="Fixed Asset Transfer")
@RequiredArgsConstructor
public class FixedAssetTransferController {


    private final FixedAssetTransferService fixedAssetTransferService;
    private final PermissionEvaluator permissionEvaluator;

    /*
      Endpoint to pre-fetch the next return number for the UI.
     */
    @GetMapping("/next-transfer-number")
    public Map<String, String> getNextTransferNumber(@PathVariable UUID tenantId) {
        String nextNumber = fixedAssetTransferService.generateFixedAssetTransferNumber(tenantId);
        return Map.of("transferNumber", nextNumber);
    }



    @PostMapping("/add")
    public ResponseEntity<?> addFixedAssetTransfer(
            @PathVariable UUID tenantId,
            @RequestBody @Valid FixedAssetTransferRequest request) {
        permissionEvaluator.addFixedAssetTransfer(tenantId);
        FixedAssetTransferResponse response = fixedAssetTransferService.addFixedAssetTransfer(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/get-all")

    public ResponseEntity<Page<FixedAssetTransferResponse>> getAllFixedAssetTransfers(
            @PathVariable UUID tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        permissionEvaluator.getAllFixedAssetTransferPermission(tenantId);

        Page<FixedAssetTransferResponse> response=fixedAssetTransferService.getAllFixedAssetTransfer(tenantId, page, size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-transfer-number/{transferNumber}")
    public ResponseEntity<?> getFixedAssetTransferByTransferNumber(
            @PathVariable UUID tenantId,
            @PathVariable String transferNumber) {

        permissionEvaluator.getAllFixedAssetTransferByTransferNoPermission(tenantId);

        FixedAssetTransferResponse response= fixedAssetTransferService.getFixedAssetTransferByTransferNumber(tenantId, transferNumber);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFixedAssetTransferById(
            @PathVariable UUID tenantId,
            @PathVariable UUID id) {
        permissionEvaluator.getFixedAssetTransferByIdPermission(tenantId,id);

        FixedAssetTransferResponse response=fixedAssetTransferService.getFixedAssetTransferById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFixedAssetTransfer(
            @PathVariable UUID tenantId,
            @PathVariable UUID id,
            @RequestBody @Valid FixedAssetTransferRequest request) {
        permissionEvaluator.updateFixedAssetTransferPermission(tenantId);

        FixedAssetTransferResponse updated=fixedAssetTransferService.updateFixedAssetTransfer(tenantId, id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a fixed asset transfer")
    public void deleteFixedAssetTransfer(
            @PathVariable UUID tenantId,
            @PathVariable UUID id) {
        fixedAssetTransferService.deleteFixedAssetTransfer(tenantId, id);
    }



}
