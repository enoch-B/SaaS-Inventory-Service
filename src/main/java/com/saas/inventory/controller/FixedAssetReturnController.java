package com.saas.inventory.controller;


import com.saas.inventory.dto.request.FixedAssetReturn.FixedAssetReturnRequest;
import com.saas.inventory.dto.response.FixedAssetReturn.FixedAssetReturnResponse;
import com.saas.inventory.service.FixedAssetReturnService;
import com.saas.inventory.utility.PermissionEvaluator;

import feign.Response;
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

import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/inventory/fixed-asset-return/{tenantId}")
@Tag(name="Fixed Asset Return")
@RequiredArgsConstructor
public class FixedAssetReturnController {
    private final FixedAssetReturnService fixedAssetReturnService;
    private final PermissionEvaluator permissionEvaluator;

      /* Endpoint to pre-fetch the next return number for the UI.
       */
    @GetMapping("/next-return-number")
    public Map<String, String> getNextReturnNumber(@PathVariable UUID tenantId) {
        String nextNumber = fixedAssetReturnService.generateFixedAssetTransReturnNumber(tenantId);
        return Map.of("returnNumber", nextNumber);
    }


    @PostMapping("/add")
    public ResponseEntity<?> addFixedAssetReturn(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Valid @RequestBody FixedAssetReturnRequest request) {

        permissionEvaluator.addFixedAssetReturnPermission(tenantId);

        FixedAssetReturnResponse response=fixedAssetReturnService.addFixedAssetReturn(tenantId, request);

        return new  ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity< Page<FixedAssetReturnResponse>> getAllFixedAssetReturns(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        permissionEvaluator.getAllFixedAssetReturnPermission(tenantId);
        Page<FixedAssetReturnResponse> response = fixedAssetReturnService.getAllFixedAssetReturns(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getFixedAssetReturnById(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Fixed Asset Return ID", required = true) @PathVariable UUID id) {

         permissionEvaluator.getFixedAssetReturnByIdPermission(tenantId);
        FixedAssetReturnResponse response = fixedAssetReturnService.getFixedAssetReturnById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFixedAssetReturn(
            @Parameter(description = "Tenant ID", required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Fixed Asset Return ID") @PathVariable UUID id) {

         permissionEvaluator.deleteFixedAssetReturnPermission(tenantId);
        fixedAssetReturnService.deleteFixedAssetReturn(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PutMapping("/update/{returnId}")
    public ResponseEntity<FixedAssetReturnResponse> updateFixedAssetReturn(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Fixed Asset Return ID") @PathVariable UUID returnId,
            @RequestBody @Valid FixedAssetReturnRequest request) {

         permissionEvaluator.updateFixedAssetReturnPermission(tenantId);

        FixedAssetReturnResponse updated = fixedAssetReturnService.updateFixedAssetReturn(tenantId, returnId, request);
        return ResponseEntity.ok(updated);
    }

}
