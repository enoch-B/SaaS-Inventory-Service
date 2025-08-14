package com.saas.inventory.controller;


import com.saas.inventory.dto.request.InventoryCount.InventoryCountRequest;
import com.saas.inventory.dto.response.InventoryCount.InventoryCountResponse;
import com.saas.inventory.service.InventoryCountService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/inventory/inventory-count/{tenantId}")
@Tag(name="Inventory Count")
@RequiredArgsConstructor
public class InventoryCountController {
    private final InventoryCountService inventoryCountService;
    private final PermissionEvaluator permissionEvaluator;

    /*
    Endpoint to pre-fetch the next Count number for the UI.
    */
    @GetMapping("/next-count-number")
    public Map<String, String> getNextReturnNumber(@PathVariable UUID tenantId) {
        String nextNumber = inventoryCountService.generateInventoryCountNumber(tenantId);
        return Map.of("countNumber", nextNumber);
    }


    @PostMapping("/add")
    public ResponseEntity<?> createInventoryCount(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Count request body") @RequestBody @Valid InventoryCountRequest request) {

//        permissionEvaluator.addInventoryCountPermission(tenantId);

        InventoryCountResponse response=inventoryCountService.createInventoryCount(tenantId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getInventoryCountById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Count ID") @PathVariable UUID id) {

//        permissionEvaluator.getInventoryCountByIdPermission(tenantId);
        InventoryCountResponse response = inventoryCountService.getInventoryCountById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<InventoryCountResponse>> getInventoryCountsPaginated(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

//         permissionEvaluator.getAllInventoryCountPermission(tenantId);
        Page<InventoryCountResponse> response = inventoryCountService.getPaginatedInventoryCounts(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

   @GetMapping("/get-by/{inventoryCountNumber}")
    public ResponseEntity<?> getInventoryCountByCountNo(@PathVariable UUID tenantId,
                                                        @PathVariable String inventoryCountNumber){
//        permissionEvaluator.getInventoryCountByCountNumber(tenantId);

        InventoryCountResponse response=inventoryCountService.getInventoryCountByCountNo(tenantId,inventoryCountNumber);

        return ResponseEntity.ok(response);
   }

   @PutMapping("/update/{id}")
    public ResponseEntity<InventoryCountResponse> updateInventoryCount(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Count ID") @PathVariable UUID id,
            @Parameter(description = "Updated inventory count request body") @RequestBody @Valid InventoryCountRequest inventoryCountRequest) {

//        permissionEvaluator.updateInventoryCountPermission(tenantId);
        InventoryCountResponse response = inventoryCountService.updateInventoryCount(tenantId, id, inventoryCountRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInventoryCount(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Inventory Count ID") @PathVariable UUID id) {

//        permissionEvaluator.deleteInventoryCountPermission(tenantId);

        inventoryCountService.deleteInventoryCount(tenantId, id);
        return ResponseEntity.ok("Inventory Count Deleted Successfully");
    }




}
