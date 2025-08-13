package com.saas.inventory.event;

import com.saas.inventory.data.ResourceData;
import com.saas.inventory.dto.eventDto.ResourceEvent;
import com.saas.inventory.dto.eventDto.ResourceStatusEvent;
import com.saas.inventory.model.Resource;
import com.saas.inventory.repository.ResourceRepository;
import com.saas.inventory.utility.ResourceEventContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceEventConsumer {

    private final ResourceData resourceData;
    private final ResourceRepository resourceRepository;

    @RabbitListener(queues = "${rabbitmq.create-resource-queue}")
    public void consumeCreateResourceEvent(ResourceEvent event) {
        log.info("Received create resource event: {}", event);
        try {
            ResourceEventContext.set(event);
            resourceData.saveResource(event);
        } catch (Exception e) {
            log.error("Error while processing create resource event: {}", event, e);
        } finally {
            ResourceEventContext.clear();
        }
    }

    @RabbitListener(queues = "${rabbitmq.delete-resource-queue}")
    public void consumeDeleteTenantEvent(UUID tenantId) {
        log.info("Received delete resource event: {}", tenantId);
        try {
            List<Resource> resources = resourceRepository.findByTenantId(tenantId);
            resourceRepository.deleteAll(resources);
        } catch (Exception e) {
            log.error("Error while deleting resources for tenantId: {}", tenantId, e);
        }
    }

    @RabbitListener(queues = "${rabbitmq.change-resource-status-queue}")
    public void consumeResourceStatusEvent(ResourceStatusEvent event) {
        log.info("Received change resource status event: {}", event);
        try {
            List<Resource> resources = resourceRepository.findByTenantId(event.getTenantId());
            List<Resource> updatedResources = new ArrayList<>();
            for (Resource resource : resources) {
                resource.setStatus(event.getStatus());
                updatedResources.add(resource);
            }
            resourceRepository.saveAll(updatedResources);
        } catch (Exception e) {
            log.error("Error while changing resource status for tenantId: {}", event.getTenantId(), e);
        }
    }
}
