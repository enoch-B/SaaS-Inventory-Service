package com.saas.inventory.dto.clientDto;


import com.saas.inventory.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantDto {
    private UUID id;
    private String tenantName;
    private String abbreviatedName;
    private LocalDate establishedYear;
    private String mission;
    private String vision;
    private String logoName;
    private String logoType;
    private byte[] logoBytes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
