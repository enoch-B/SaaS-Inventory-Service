package com.saas.inventory.utility;

import com.saas.inventory.enums.ResourceName;
import com.saas.inventory.model.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final PermissionUtil permissionUtil;

    /* Resource Permission */
    public void getAllResourcesPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, ResourceName.GET_ALL_RESOURCES);
        }
    }

    public void getResourcesByRoleNamePermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_RESOURCES_BY_ROLE_NAME);
    }

    public void getResourceByIdPermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            checkPermission(tenantId, ResourceName.GET_RESOURCE_BY_ID);
        }
    }

    public void getResourceByNamePermission(UUID tenantId) {
        boolean isAdmin = permissionUtil.isAdmin();
        if (!isAdmin) {
            permissionUtil.isTenantUser(tenantId);
        }
    }

    public void grantResourceAccessToRolePermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GRANT_RESOURCE_ACCESS_TO_ROLE);
    }

    public void revokeResourceAccessFromRolePermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.REVOKE_RESOURCE_ACCESS_FROM_ROLE);
    }

    private void checkPermission(UUID tenantId, ResourceName resourceName) {
        boolean hasPermission = permissionUtil.hasPermission(tenantId, resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }

      /* Disposal Collection Permission/ Disposable Asset */
    public void addDisposalCollectionPermission(UUID tenantId) {
      checkPermission(tenantId, ResourceName.ADD_DISPOSAL_COLLECTION);
    }

    public void getAllDisposalCollectionPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_DISPOSAL_COLLECTION);
    }

    public void getDisposalCollectionByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_DISPOSAL_COLLECTION_BY_ID);
    }

    public void getDisposalCollectionByDrNoPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_DISPOSAL_COLLECTION_BY_DR_NO);
    }

    public void deleteDisposalCollectionPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_DISPOSAL_COLLECTION);
    }

    public void updateDisposalCollectionPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_DISPOSAL_COLLECTION);
    }

    /* Fixed Asset Disposal Permission */


    public void addFixedAssetDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.ADD_FIXED_ASSET_DISPOSAL);
    }
    public void updateFixedAssetDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_FIXED_ASSET_DISPOSAL);
    }
    public void getFixedAssetDisposalByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_FIXED_ASSET_DISPOSAL_BY_ID);
    }
    public void getAllFixedAssetDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_FIXED_ASSET_DISPOSAL);
    }


     /* Need Assessment Permission */
    public void addNeedAssessmentPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.ADD_NEED_ASSESSMENT);
    }

    public void getAllNeedAssessmentPermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.GET_ALL_NEED_ASSESSMENT);
    }

    public void getNeedAssessmentById(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_NEED_ASSESSMENT_BY_ID);
    }

    public void deleteNeedAssessmentPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_NEED_ASSESSMENT);
    }

    public void updateNeedAssessmentPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_NEED_ASSESSMENT);
    }


       /*
         Fixed Asset Return Permission
        */
    public void addFixedAssetReturnPermission(UUID tenantId) {
            checkPermission(tenantId, ResourceName.ADD_FIXED_ASSET_RETURN);
    }

    public void getAllFixedAssetReturnPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_FIXED_ASSET_RETURN);
    }

    public void getFixedAssetReturnByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_FIXED_ASSET_RETURN_BY_ID);
    }

    public void deleteFixedAssetReturnPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_FIXED_ASSET_RETURN);
    }

    public void updateFixedAssetReturnPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_FIXED_ASSET_RETURN);
    }

     /*
        Fixed Asset Transfer Permission
      */
    public void addFixedAssetTransfer(UUID tenantId) {
        checkPermission(tenantId, ResourceName.ADD_FIXED_ASSET_TRANSFER);
    }

    public void getAllFixedAssetTransferPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_FIXED_ASSET_TRANSFER);
    }

    public void getAllFixedAssetTransferByTransferNoPermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.GET_FIXED_ASSET_TRANSFER_BY_TRANSFER_NO);
    }

    public void getFixedAssetTransferByIdPermission(UUID tenantId, UUID transferId) {
        checkPermission(tenantId, ResourceName.GET_FIXED_ASSET_TRANSFER_BY_ID);
    }

    public void updateFixedAssetTransferPermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.UPDATE_FIXED_ASSET_TRANSFER);
    }

    /*
      Inventory Count Permission
     */

    public void addInventoryCountPermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.ADD_INVENTORY_COUNT);

    }

    public void getInventoryCountByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_INVENTORY_COUNT_BY_ID);
    }

    public void getAllInventoryCountPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_INVENTORY_COUNT);

    }

    public void getInventoryCountByCountNumber(UUID tenantId) {
            checkPermission(tenantId,ResourceName.GET_INVENTORY_COUNT_BY_COUNT_NO);
    }

    public void updateInventoryCountPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_INVENTORY_COUNT);
    }

    public void deleteInventoryCountPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_INVENTORY_COUNT);
    }

    public void addLostFixedAssetPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.ADD_LOST_FIXED_ASSET);
    }
   /* Lost Fixed Asset Permission */

    public void getAllLostFixedAssetPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_LOST_FIXED_ASSET);
    }

    public void getLostFixedAssetByIdPermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.GET_LOST_FIXED_ASSET_BY_ID);
    }

    public void updateLostFixedAssetTransferPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_LOST_FIXED_ASSET);
    }

    public void deleteLostFixedAssetPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_LOST_FIXED_ASSET);
    }

    public void getByLostItemNoPermission(UUID tenantId) {
            checkPermission(tenantId, ResourceName.GET_LOST_FIXED_ASSET_BY_LOST_ITEM_NO);
    }

        /*  Lost stock Item Permission  */

    public void addLostStockItemPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.ADD_LOST_STOCK_ITEM);
    }

    public void getAllLostStockItemPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_LOST_STOCK_ITEM);
    }

    public void getLostStockItemByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_LOST_STOCK_ITEM_BY_ID);
    }

    public void deleteLostStockItemPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_LOST_STOCK_ITEM);
    }

    public void updateLostStockItemPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_LOST_STOCK_ITEM);
    }

    public void getLostStockItemByLostStockItemNoPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_LOST_STOCK_ITEM_BY_LOST_STOCK_ITEM_NO);
    }
     /*
      Inventory Balance
      */
    public void addInventoryBalancePermission(UUID tenantId) {
        checkPermission(tenantId,ResourceName.ADD_INVENTORY_BALANCE);
    }

    public void getAllInventoryBalancePermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_INVENTORY_BALANCE);
    }

    public void getInventoryBalanceByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_INVENTORY_BALANCE_BY_ID);
    }
    public void updateInventoryBalancePermission(UUID tenantId){
        checkPermission(tenantId, ResourceName.UPDATE_INVENTORY_BALANCE);
    }
    public void deleteInventoryBalancePermission(UUID tenantId){
        checkPermission(tenantId, ResourceName.DELETE_INVENTORY_BALANCE);
    }

    /*
      Stock Disposal Permission
     */

    public void addStockDisposalPermission(UUID tenantId) {
            checkPermission(tenantId, ResourceName.ADD_STOCK_DISPOSAL);
    }

    public void updateStockDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.UPDATE_STOCK_DISPOSAL);
    }

    public void getAllStockDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_ALL_STOCK_DISPOSAL);
    }
    public void getStockDisposalByIdPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_STOCK_DISPOSAL_BY_ID);
    }
    public void getStockDisposalByDisposalNumberPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.GET_STOCK_DISPOSAL_BY_DISPOSAL_NO);
    }
    public void deleteStockDisposalPermission(UUID tenantId) {
        checkPermission(tenantId, ResourceName.DELETE_STOCK_DISPOSAL);
    }


}
