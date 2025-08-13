package com.saas.inventory.client;

import com.saas.inventory.dto.clientDto.FixedAssetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "fixed-asset-service", path = "/api/fixed-asset")
public interface FixedAssetServiceClient {

     @GetMapping("{tenantId}/assets/{id}")
     FixedAssetDto getAssetById(@PathVariable UUID tenantId, @PathVariable UUID id);



}
