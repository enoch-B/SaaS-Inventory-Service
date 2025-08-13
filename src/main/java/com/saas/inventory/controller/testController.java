package com.saas.inventory.controller;


import com.saas.inventory.client.AuthServiceClient;
import com.saas.inventory.client.ItemServiceClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name="Test")
public class testController {

        private final ItemServiceClient itemServiceClient;

        @GetMapping("/items/{tenantId}/get/{id}")
        public ResponseEntity<?> getItemById(@PathVariable UUID tenantId, @PathVariable UUID id) {
            return ResponseEntity.ok(itemServiceClient.getItemById(id,tenantId));
        }
    }


