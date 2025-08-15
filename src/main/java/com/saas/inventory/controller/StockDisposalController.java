package com.saas.inventory.controller;


import com.saas.inventory.dto.request.StockDisposal.StockDisposalRequest;
import com.saas.inventory.dto.response.StockDisposal.StockDisposalResponse;
import com.saas.inventory.service.StockDisposalService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/stock-disposal/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Stock Disposal")
public class StockDisposalController {
    private final StockDisposalService stockDisposalService;
    private final PermissionEvaluator permissionEvaluator;

    /* Endpoint to pre-fetch the next return number for the UI.
     */
    @GetMapping("/next-disposal-number")
    public Map<String, String> getNextDisposalNumber(@PathVariable UUID tenantId) {
        String nextNumber = stockDisposalService.generateDisposalNo(tenantId);
        return Map.of("disposalNumber", nextNumber);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StockDisposalResponse> addStockDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Stock Disposal request body") @RequestPart(value = "request") @Valid StockDisposalRequest request,
            @Parameter(description = "File upload") @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        permissionEvaluator.addStockDisposalPermission(tenantId);
        StockDisposalResponse response = stockDisposalService.addStockDisposal(tenantId, request, file);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateStockDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Stock Disposal ID") @PathVariable UUID id,
             @Valid @RequestPart("request") StockDisposalRequest request,
            @Parameter(description = "File upload") @RequestPart("file") MultipartFile file) throws IOException {

         permissionEvaluator.updateStockDisposalPermission(tenantId);

        StockDisposalResponse updated = stockDisposalService.updateStockDisposal(tenantId, id, request, file);

        return ResponseEntity.ok(updated);
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<StockDisposalResponse>> getAllStockDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        permissionEvaluator.getAllStockDisposalPermission(tenantId);
        Page<StockDisposalResponse> response = stockDisposalService.getAllStockDisposal(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDisposalResponse> getStockDisposalById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Stock Disposal ID") @PathVariable UUID id) {
        permissionEvaluator.getStockDisposalByIdPermission(tenantId);

        StockDisposalResponse response = stockDisposalService.getStockDisposalById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by/{disposalNumber}")
    public ResponseEntity<?> getStockDisposalBySDNumber(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Stock Disposal Number") @PathVariable String disposalNumber ) {

        permissionEvaluator.getStockDisposalByDisposalNumberPermission(tenantId);

        StockDisposalResponse response = stockDisposalService.getStockDisposalBySDNumber(tenantId, disposalNumber);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStockDisposal(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Stock Disposal ID") @PathVariable UUID id) {

        permissionEvaluator.deleteStockDisposalPermission(tenantId);
        stockDisposalService.deleteStockDisposal(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }


}
