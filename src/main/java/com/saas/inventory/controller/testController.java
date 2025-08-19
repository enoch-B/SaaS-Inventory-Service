package com.saas.inventory.controller;


import com.saas.inventory.client.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name="Test")
public class testController {

    private final ItemServiceClient itemServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final StoreServiceClient storeServiceClient;
    private final FixedAssetServiceClient fixedAssetServiceClient;
    private final OrganizationServiceClient organizationServiceClient;

    @GetMapping("item/items/{tenantId}/get/{id}")
    public ResponseEntity<?> getItemById(@PathVariable UUID tenantId, @PathVariable UUID id) {
        return ResponseEntity.ok(itemServiceClient.getItemById(id, tenantId));
    }

    @GetMapping("/leave/budget-years/{tenantId}/get/{id}")
    public ResponseEntity<?> getBudgetYearById(@PathVariable UUID tenantId, @PathVariable UUID id) {
        return ResponseEntity.ok(leaveServiceClient.getBudgetYearById(id, tenantId));
    }

    @GetMapping("/store/stores/{tenantId}/get/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable UUID tenantId, @PathVariable UUID id) {
        return ResponseEntity.ok(storeServiceClient.getStoreById(tenantId, id));
    }

    @GetMapping("/fixed-asset/{tenantId}/get/{id}")
    public ResponseEntity<?> getFixedAssetById(@PathVariable UUID tenantId, @PathVariable UUID id) {
        return ResponseEntity.ok(fixedAssetServiceClient.getAssetById(tenantId, id));
    }

    @GetMapping("/organization/departments/{tenantId}/get/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable UUID tenantId, @PathVariable UUID id) {
        return ResponseEntity.ok(organizationServiceClient.getDepartmentById(tenantId, id));
    }
}


