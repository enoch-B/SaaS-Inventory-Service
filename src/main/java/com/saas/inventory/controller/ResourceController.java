package com.saas.inventory.controller;

import com.saas.inventory.dto.response.ResourceResponse;
import com.saas.inventory.enums.ResourceStatus;
import com.saas.inventory.service.ResourceService;
import com.saas.inventory.utility.PermissionEvaluator;
import com.saas.inventory.utility.SecurityUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/resources/{tenantId}")
@RequiredArgsConstructor
@Tag(name = "Resource")
public class ResourceController {

    private final ResourceService resourceService;
    private final PermissionEvaluator permissionEvaluator;
    private final SecurityUtil securityUtil;

    @PostMapping("/add")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> addResources(
            @PathVariable UUID tenantId) {

        resourceService.addResource(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body("Resources added successfully.");
    }



    @GetMapping("/get-all")
    public ResponseEntity<?> getAllResources(
            @PathVariable UUID tenantId) {

        permissionEvaluator.getAllResourcesPermission(tenantId);

        List<ResourceResponse> response = resourceService.getAllResources(tenantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/{resource-id}")
    public ResponseEntity<?> getResourceById(
            @PathVariable UUID tenantId,
            @PathVariable("resource-id") UUID resourceId) {

        permissionEvaluator.getResourceByIdPermission(tenantId);

        ResourceResponse response = resourceService.getResourceById(tenantId, resourceId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/get/resource-name")
    public ResponseEntity<?> getResourceByName(
            @PathVariable UUID tenantId,
            @RequestParam String resourceName) {

        permissionEvaluator.getResourceByNamePermission(tenantId);

        ResourceResponse response = resourceService.getResponseByName(tenantId, resourceName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<?> getResourcesByRole(
            @PathVariable UUID tenantId,
            @PathVariable String roleName) {

        permissionEvaluator.getResourcesByRoleNamePermission(tenantId);

        List<ResourceResponse> response = resourceService.getResourcesByRole(tenantId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/role/grant-access/{resourceId}/{roleName}")
    public ResponseEntity<?> giveResourceAccessToRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @PathVariable String roleName) {

        permissionEvaluator.grantResourceAccessToRolePermission(tenantId);

        String response = resourceService
                .grantResourceAccessToRole(tenantId, resourceId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/role/revoke-access/{resourceId}/{roleName}")
    public ResponseEntity<?> removeResourceAccessFromRole(
            @PathVariable UUID tenantId,
            @PathVariable UUID resourceId,
            @PathVariable String roleName) {

        permissionEvaluator.revokeResourceAccessFromRolePermission(tenantId);

        String response = resourceService
                .revokeResourceAccessFromRole(tenantId, resourceId, roleName);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/change-status")
    public ResponseEntity<?> changeResourcesStatus(
            @PathVariable UUID tenantId,
            @RequestParam ResourceStatus status) {

        List<ResourceResponse> response = resourceService
                .changeResourcesStatus(tenantId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
