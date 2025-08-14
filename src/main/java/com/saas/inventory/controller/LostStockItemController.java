package com.saas.inventory.controller;

import com.saas.inventory.dto.request.LostStockItem.LostStockItemRequest;
import com.saas.inventory.dto.response.LostStockItem.LostStockItemResponse;
import com.saas.inventory.service.LostStockItemService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/lost-stock-item/{tenantId}")
@Tag(name = "Lost Stock Item")
@RequiredArgsConstructor
public class LostStockItemController {
    private final LostStockItemService lostStockItemService;
    private final PermissionEvaluator permissionEvaluator;


    /* Endpoint to pre-fetch the next return number for the UI.
     */
    @GetMapping("/next-Item-number")
    public Map<String, String> getNextReturnNumber(@PathVariable UUID tenantId) {
        String nextNumber = lostStockItemService.generateLostStockItemNo(tenantId);
        return Map.of("itemNumber", nextNumber);
    }


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addLostStockItem(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @RequestPart("request") @Valid LostStockItemRequest request,
            @Parameter(description = "Optional file upload") @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

//        permissionEvaluator.addLostStockItemPermission(tenantId);

        LostStockItemResponse response = lostStockItemService.addLostStockItem(tenantId, request, file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<LostStockItemResponse>> getAllLostStockItem(
            @Parameter(description = "Tenant ID",required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

//        permissionEvaluator.getAllLostStockItemPermission(tenantId);
        Page<LostStockItemResponse> response = lostStockItemService.getAllLostStockItem(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLostStockItemById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Stock Item ID") @PathVariable UUID id) {

//          permissionEvaluator.getLostStockItemByIdPermission(tenantId);

        LostStockItemResponse response = lostStockItemService.getLostStockItemById(tenantId, id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/{LostStockItemNo}")

    public ResponseEntity<?> getLostStockItemByLostStockItemNo(@PathVariable UUID tenantId, @PathVariable String lostStockItemNo){

//        permissionEvaluator.getLostStockItemByLostStockItemNoPermission(tenantId);

        LostStockItemResponse response=lostStockItemService.getLostStockItemByLSNo(tenantId, lostStockItemNo);

        return ResponseEntity.ok(response);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLostStockItem(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Lost Stock Item ID") @PathVariable UUID id) {

//        permissionEvaluator.deleteLostStockItemPermission(tenantId);

        lostStockItemService.deleteLostStockItem(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateLostStockItem(
            @Parameter(description = "Tenant ID",required = true) @PathVariable UUID tenantId,
              @PathVariable UUID id,
             @RequestPart("request") @Valid LostStockItemRequest request,
            @Parameter(description = "Optional file upload") @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
//        permissionEvaluator.updateLostStockItemPermission(tenantId);

        LostStockItemResponse updated = lostStockItemService.updateLostStockItem(tenantId, id, request, file);
        return ResponseEntity.ok(updated);
    }
}
