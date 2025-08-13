package com.saas.inventory.controller;

import com.saas.inventory.dto.request.NeedAssessment.NeedAssessmentRequest;
import com.saas.inventory.dto.response.NeedAssessment.NeedAssessmentResponse;
import com.saas.inventory.service.NeedAssessmentService;
import com.saas.inventory.utility.PermissionEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory/need-assessment/{tenantId}")
@Tag(name = "Need Assessment")
@RequiredArgsConstructor
public class NeedAssessmentController {

    private final NeedAssessmentService needAssessmentService;
    private final PermissionEvaluator permissionEvaluator;
    @PostMapping("/add")
    public ResponseEntity<NeedAssessmentResponse> addNeedAssessment(
            @Parameter(description = "Tenant ID",required = true) @PathVariable UUID tenantId,
            @Parameter(description = "Need Assessment request body") @RequestBody @Valid NeedAssessmentRequest needAssessmentRequest) {

//        permissionEvaluator.addNeedAssessmentPermission(tenantId);


        NeedAssessmentResponse response = needAssessmentService.addNeedAssessment(tenantId, needAssessmentRequest);
        return new  ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<NeedAssessmentResponse>> getAllNeedAssessmentPaginated(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {

        permissionEvaluator.getAllNeedAssessmentPermission(tenantId);
        Page<NeedAssessmentResponse> response = needAssessmentService.getAllNeedAssessmentPaginated(tenantId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<NeedAssessmentResponse> getNeedAssessmentById(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Need Assessment ID") @PathVariable UUID id) {

        permissionEvaluator.getNeedAssessmentById(tenantId);
        NeedAssessmentResponse response = needAssessmentService.getNeedAssessmentById(tenantId, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNeedAssessment(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Need Assessment ID") @PathVariable UUID id) {

        permissionEvaluator.deleteNeedAssessmentPermission(tenantId);
        needAssessmentService.deleteNeedAssessment(tenantId, id);
        return ResponseEntity.ok("Deleted Successfully");
    }

    @PutMapping("/update/{assessmentId}")
    public ResponseEntity<NeedAssessmentResponse> updateNeedAssessment(
            @Parameter(description = "Tenant ID") @PathVariable UUID tenantId,
            @Parameter(description = "Need Assessment ID") @PathVariable UUID id,
            @Parameter(description = "Updated need assessment request body") @RequestBody @Valid NeedAssessmentRequest request) throws IOException {

     permissionEvaluator.updateNeedAssessmentPermission(tenantId);
        NeedAssessmentResponse updated = needAssessmentService.updateNeedAssessment(tenantId, id, request);
        return ResponseEntity.ok(updated);
    }


}
