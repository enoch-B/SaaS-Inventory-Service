package com.saas.inventory.model;

import com.saas.inventory.dto.eventDto.ResourceEvent;
import com.saas.inventory.utility.ResourceEventContext;
import com.saas.inventory.utility.SecurityUtil;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseEntityListener {

    private final SecurityUtil securityUtil;

    @PrePersist
    public void setCreatedByAndUpdatedBy(Base base) {
        ResourceEvent resourceEvent = ResourceEventContext.get();
        if(resourceEvent != null && resourceEvent.getCreatedBy() != null) {
            base.setCreatedBy(resourceEvent.getCreatedBy());
        } else {
            String name = securityUtil.getName();
            base.setCreatedBy(name != null ? name : "unknown");
        }
    }

    @PreUpdate
    public void setUpdatedBy(Base base) {
        String name = securityUtil.getName();
        base.setUpdatedBy(name != null ? name : "unknown");
    }
}
