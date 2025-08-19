package com.saas.inventory.dto.clientDto;


import com.saas.inventory.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto extends BaseResponse {
    private UUID id;
    private String departmentName;
    private UUID departmentTypeId;
    private LocalDate establishedDate;
    private UUID parentDepartmentId;
    private List<UUID> subDepartmentIds;
}
