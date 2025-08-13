package com.saas.inventory.client;


import com.saas.inventory.dto.clientDto.StoreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "store-service", path = "/api/store")
public interface StoreServiceClient {
    @GetMapping("/stores/{tenantId}/get/{id}")
    StoreDto getStoreById(@PathVariable UUID tenantId,@PathVariable("id") UUID id);

}
