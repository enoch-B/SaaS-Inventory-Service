package com.saas.inventory.client;


import com.saas.inventory.dto.clientDto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "auth-service", path = "/api/auth")
public interface AuthServiceClient {
    @GetMapping("/users/{tenantId}/get/username")
    UserDto getUserByUsername(@PathVariable UUID tenantId,
                              @RequestParam String username);
}
