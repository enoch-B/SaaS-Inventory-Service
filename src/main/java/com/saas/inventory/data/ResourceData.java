package com.saas.inventory.data;

import com.saas.inventory.dto.eventDto.ResourceEvent;
import com.saas.inventory.enums.ResourceName;
import com.saas.inventory.mapper.ResourceMapper;
import com.saas.inventory.model.Resource;
import com.saas.inventory.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ResourceData {

    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;

    public void saveResource(ResourceEvent eventResponse) {

        Set<Resource> resources = new HashSet<>();

        String defaultRole = "default_role";

        /* Resource */
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_ALL_RESOURCES.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_RESOURCES_BY_ROLE_NAME.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_RESOURCE_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_RESOURCE_BY_NAME.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GRANT_RESOURCE_ACCESS_TO_ROLE.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .REVOKE_RESOURCE_ACCESS_FROM_ROLE.getValue(), null, eventResponse));


        List<Resource> existedResources = resourceRepository.findByTenantId(eventResponse.getTenantId());
        resourceRepository.deleteAll(existedResources);
        resourceRepository.saveAll(resources);
    }
}
