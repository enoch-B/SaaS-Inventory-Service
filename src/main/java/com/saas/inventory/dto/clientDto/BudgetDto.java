package com.saas.inventory.dto.clientDto;

import com.saas.inventory.dto.response.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class BudgetDto extends BaseResponse {
    private UUID id;
    private String budgetYear;
    private String description;
    private boolean active;
}
