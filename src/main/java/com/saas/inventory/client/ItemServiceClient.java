package com.saas.inventory.client;


import com.saas.inventory.dto.clientDto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "item-service", path = "/api/item")
public interface ItemServiceClient {
   @GetMapping("/items/{tenantId}/get/{id}")
    ItemDto getItemById(@PathVariable("tenantId") UUID tenantId
            ,@PathVariable("id") UUID itemId);
}
