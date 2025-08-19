package com.saas.inventory.client;


import com.saas.inventory.dto.clientDto.BudgetDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "leave-service", path = "/api/leave")
public interface LeaveServiceClient {
    @GetMapping("/budgetYears/{tenantId}/get/{id}")
    BudgetDto getBudgetYearById(@PathVariable UUID tenantId, @PathVariable UUID id);


}
