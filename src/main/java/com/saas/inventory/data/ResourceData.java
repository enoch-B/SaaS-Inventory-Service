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


         /*
           Disposable asset collection
          */

        resources.add(resourceMapper.mapToEntity(ResourceName
                .ADD_DISPOSAL_COLLECTION.getValue(),null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_ALL_DISPOSAL_COLLECTION.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_DISPOSAL_COLLECTION_BY_DR_NO.getValue(), defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_DISPOSAL_COLLECTION_BY_ID.getValue(), defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .UPDATE_DISPOSAL_COLLECTION.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .DELETE_DISPOSAL_COLLECTION.getValue(), null,eventResponse));

               /* Fixed Asset Disposal*/
        resources.add(resourceMapper.mapToEntity(ResourceName
                .ADD_FIXED_ASSET_DISPOSAL.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_ALL_FIXED_ASSET_DISPOSAL.getValue(), defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_FIXED_ASSET_DISPOSAL_BY_ID.getValue(),defaultRole,eventResponse ));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .GET_FIXED_ASSET_DISPOSAL_BY_DISPOSAL_NO.getValue(), defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.
                        UPDATE_FIXED_ASSET_DISPOSAL.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .DELETE_FIXED_ASSET_DISPOSAL.getValue(), null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName
                .DOWNLOAD_FIXED_ASSET_DISPOSAL_FILE.getValue(), null,eventResponse));
        /* Fixed Asset Return */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_FIXED_ASSET_RETURN.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_FIXED_ASSET_RETURN.getValue(),defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_FIXED_ASSET_RETURN_BY_ID.getValue(),defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_FIXED_ASSET_RETURN.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_FIXED_ASSET_RETURN.getValue(),null,eventResponse));

           /* Fixed Asset Transfer */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_FIXED_ASSET_TRANSFER.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_FIXED_ASSET_TRANSFER.getValue(),defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_FIXED_ASSET_TRANSFER_BY_ID.getValue(),defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_FIXED_ASSET_TRANSFER_BY_TRANSFER_NO.getValue(),defaultRole,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_FIXED_ASSET_TRANSFER.getValue(),null,eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_FIXED_ASSET_TRANSFER.getValue(),null,eventResponse));

        /* Inventory Count */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_INVENTORY_COUNT.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_INVENTORY_COUNT_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_INVENTORY_COUNT_BY_COUNT_NO.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_INVENTORY_COUNT.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_INVENTORY_COUNT.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_INVENTORY_COUNT.getValue(), null, eventResponse));

        /* Inventory Balance */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_INVENTORY_BALANCE.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_INVENTORY_BALANCE_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_INVENTORY_BALANCE.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_INVENTORY_BALANCE.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_INVENTORY_BALANCE.getValue(), null, eventResponse));

        /* Lost Fixed Asset */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_LOST_FIXED_ASSET.getValue(),null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_LOST_FIXED_ASSET.getValue(),defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_LOST_FIXED_ASSET_BY_ID.getValue(),defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_LOST_FIXED_ASSET_BY_LOST_ITEM_NO.getValue(),defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_LOST_FIXED_ASSET.getValue(),null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_LOST_FIXED_ASSET.getValue(),null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DOWNLOAD_LOST_FIXED_ASSET_FILE.getValue(),null, eventResponse));

         /* Lost Stock Item */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_LOST_STOCK_ITEM.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_LOST_STOCK_ITEM.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_LOST_STOCK_ITEM_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_LOST_STOCK_ITEM_BY_LOST_STOCK_ITEM_NO.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_LOST_STOCK_ITEM.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_LOST_STOCK_ITEM.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DOWNLOAD_LOST_STOCK_ITEM_FILE.getValue(), null, eventResponse));

        /* Need Assessment */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_NEED_ASSESSMENT.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_NEED_ASSESSMENT.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_NEED_ASSESSMENT_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_NEED_ASSESSMENT.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_NEED_ASSESSMENT.getValue(), null, eventResponse));

        /* Stock Disposal */
        resources.add(resourceMapper.mapToEntity(ResourceName.ADD_STOCK_DISPOSAL.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_ALL_STOCK_DISPOSAL.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_STOCK_DISPOSAL_BY_ID.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.GET_STOCK_DISPOSAL_BY_DISPOSAL_NO.getValue(), defaultRole, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.UPDATE_STOCK_DISPOSAL.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DELETE_STOCK_DISPOSAL.getValue(), null, eventResponse));
        resources.add(resourceMapper.mapToEntity(ResourceName.DOWNLOAD_STOCK_DISPOSAL_FILE.getValue(), null, eventResponse));



        List<Resource> existedResources = resourceRepository.findByTenantId(eventResponse.getTenantId());
        resourceRepository.deleteAll(existedResources);
        resourceRepository.saveAll(resources);
    }
}
