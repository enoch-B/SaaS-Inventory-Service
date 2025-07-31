package com.saas.inventory.model;

import com.saas.inventory.enums.ResourceStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "resources")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource extends Base{

    @Column(nullable = false)
    private String resourceName;

    @Column(nullable = false)
    private ResourceStatus status;

    private Set<String> requiredRoles = new HashSet<>();

    @Column(nullable = false)
    private String tenantAbbreviatedName;

    private String description;
}

