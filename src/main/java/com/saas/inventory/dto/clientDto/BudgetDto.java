package com.saas.inventory.dto.clientDto;

import lombok.Data;

import java.util.UUID;

@Data
public class BudgetDto {
    private UUID id;
    private String budgetYear;
}
