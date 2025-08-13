package com.saas.inventory.dto.clientDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private UUID id;
    private String departmentName;
    private UUID departmentTypeId;
    private LocalDate establishedDate;
    private UUID parentDepartmentId;
    private List<UUID> subDepartmentIds;
}
