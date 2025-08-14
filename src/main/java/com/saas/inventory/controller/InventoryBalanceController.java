package com.saas.inventory.controller;

import com.saas.inventory.dto.request.InventoryBalance.InventoryBalanceRequest;
import com.saas.inventory.dto.response.InventoryBalance.InventoryBalanceResponse;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import com.saas.inventory.service.InventoryBalanceService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/inventory-balance/{tenantId}")
@Tag(name="InventoryBalance")
@RequiredArgsConstructor
public class InventoryBalanceController {

    private final InventoryBalanceService inventoryBalanceService;
    private final PermissionEvaluator permissionEvaluator;

    @PostMapping("/add")
    public ResponseEntity<?> createInventoryBalance(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @RequestBody @Valid InventoryBalanceRequest inventoryBalanceRequest) {

//      permissionEvaluator.addInventoryBalancePermission(tenantId);
        InventoryBalanceResponse response = inventoryBalanceService.createInventoryBalance(tenantId, inventoryBalanceRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllInventoryBalance(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Page number (default = 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size (default = 10)") @RequestParam(defaultValue = "10") int size) {
//         permissionEvaluator.getAllInventoryBalancePermission(tenantId);

        Page<InventoryBalanceResponse> response = inventoryBalanceService.getAllInventoryBalance(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getInventoryBalanceById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Balance ID") @PathVariable UUID id) {
//        permissionEvaluator.getInventoryBalanceByIdPermission(tenantId);
        InventoryBalanceResponse response = inventoryBalanceService.getInventoryBalanceById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInventoryBalance(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Balance ID") @PathVariable UUID id) {

//        permissionEvaluator.deleteInventoryBalancePermission(tenantId);

        inventoryBalanceService.deleteInventoryBalance(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateInventoryBalance(@PathVariable UUID tenantId, @PathVariable UUID id, @RequestBody InventoryBalanceRequest request, @RequestBody InventoryCount count) throws IOException{
//        permissionEvaluator.updateInventoryBalancePermission(tenantId);

        InventoryBalanceResponse response=inventoryBalanceService.updateInventoryBalance(tenantId,id,request,count);

        return ResponseEntity.ok(response);
    }

}
