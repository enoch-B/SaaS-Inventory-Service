package com.saas.inventory.utility;

import com.saas.inventory.client.*;
import com.saas.inventory.dto.clientDto.*;
import com.saas.inventory.exception.ResourceNotFoundException;
import com.saas.inventory.model.DisposalCollection.DisposableAsset;
import com.saas.inventory.model.FixedAssetDisposal.FixedAssetDisposal;
import com.saas.inventory.model.FixedAssetReturn.FixedAssetReturn;
import com.saas.inventory.model.FixedAssetTransfer.FixedAssetTransfer;
import com.saas.inventory.model.InventoryBalance.InventoryBalance;
import com.saas.inventory.model.InventoryCountSheet.InventoryCount;
import com.saas.inventory.model.LostFixedAsset.LostFixedAsset;
import com.saas.inventory.model.LostStockItem.LostStockItem;
import com.saas.inventory.model.NeedAssessment.NeedAssessment;
import com.saas.inventory.model.StockDisposal.StockDisposal;
import com.saas.inventory.repository.DispoalCollection.DisposableAssetRepository;
import com.saas.inventory.repository.FixedAssetDisposal.FixedAssetDisposalRepository;
import com.saas.inventory.repository.FixedAssetReturn.FixedAssetReturnRepository;
import com.saas.inventory.repository.FixedAssetTransfer.FixedAssetTransferRepository;
import com.saas.inventory.repository.InventoryBalance.InventoryBalanceRepository;
import com.saas.inventory.repository.InventoryCount.InventoryCountRepository;
import com.saas.inventory.repository.LostFixedAsset.LostFixedAssetRepository;
import com.saas.inventory.repository.LostStockItem.LostStockItemRepository;
import com.saas.inventory.repository.NeedAssessment.NeedAssessmentRepository;
import com.saas.inventory.repository.StockDisposal.StockDisposalRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

     private final NeedAssessmentRepository needAssessmentRepository;
     private final FixedAssetReturnRepository fixedAssetReturnRepository;
     private final FixedAssetTransferRepository fixedAssetTransferRepository;
    private final InventoryCountRepository inventoryCountRepository;
    private final LostFixedAssetRepository lostFixedAssetRepository;
    private final LostStockItemRepository lostStockItemRepository;
    private final InventoryBalanceRepository inventoryBalanceRepository;
    private final FixedAssetDisposalRepository fixedAssetDisposalRepository;
    private final StockDisposalRepository stockDisposalRepository;
    private final DisposableAssetRepository disposableAssetRepository;

     private final AuthServiceClient authServiceClient;
     private final  OrganizationServiceClient organizationServiceClient;
     private final  StoreServiceClient storeServiceClient;
     private final FixedAssetServiceClient fixedAssetServiceClient;
     private final EmployeeServiceClient employeeServiceClient;
     private final LeaveServiceClient leaveServiceClient;
     private final ItemServiceClient itemServiceClient;





    public void validateDrNo(String drNo) {
        if (drNo == null || drNo.isBlank()) {
            throw new IllegalArgumentException("Disposable Asset Number cannot be null or empty");
        }
    }

    public NeedAssessment getNeedAssessmentById(UUID tenantId, UUID assessmentId) {
        return needAssessmentRepository.findById(assessmentId)
                    .filter(na -> na.getTenantId().equals(tenantId))
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Need Assessment not found for id: " + assessmentId + " and tenant: " + tenantId));

    }

    public FixedAssetReturn getFixedAssetReturnById(UUID tenantId, UUID returnId) {
        return fixedAssetReturnRepository.findById(returnId)
                .filter(far -> far.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset Return Not Found or Tenant Mismatch"));

    }

    public FixedAssetTransfer getFixedAssetTransferById(UUID tenantId, UUID transferId) {
        return  fixedAssetTransferRepository
                .findById(transferId)
                .filter(transfer -> transfer.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset Transfer not found with id: " + transferId));

    }

    public InventoryCount getInventoryCountById(UUID tenantId, UUID countId) {
        return inventoryCountRepository
                .findById(countId)
                .filter(ic->ic.getTenantId().equals(tenantId))
                .orElseThrow(()-> new ResourceNotFoundException("Inventory Count Not Found or Tenant Mismatch"));
    }

    public LostFixedAsset getLostFixedAssetById(UUID tenantId, UUID id) {
        return lostFixedAssetRepository
                .findById(id)
                .filter(lfa->lfa.getTenantId().equals(tenantId))
                .orElseThrow(()->new ResourceNotFoundException("LostFixedAsset Not Found"));
    }

    public LostStockItem getLostStockItemById(UUID tenantId, UUID id) {
        return lostStockItemRepository
                .findById(id)
                .filter(ls->ls.getTenantId().equals(tenantId))
                .orElseThrow(()->new ResourceNotFoundException("Item Not Found"));
    }

    public InventoryBalance getInventoryBalanceById(UUID tenantId, UUID id) {
        return inventoryBalanceRepository
                .findById(id)
                .filter(ib->ib.getTenantId().equals(tenantId))
                .orElseThrow(()-> new ResourceNotFoundException("Balance Not Found"));
    }


     // feign client methods

    public UserDto getUserById(UUID tenantId, String username) {
        try {
            return authServiceClient.getUserByUsername(tenantId, username);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("User not found with username '" + username + "' ---- " + e.getMessage());
        }
    }

        public DepartmentDto getDepartmentById(UUID tenantId, UUID departmentId) {
            try {
                return organizationServiceClient.getDepartmentById(departmentId, tenantId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException(
                        "Department not found with id '" + departmentId + "' ---- " + e.getMessage());
            }
        }

        public StoreDto getStoreById(UUID tenantId, UUID storeId){
          try{
              return storeServiceClient.getStoreById(tenantId, storeId);
          } catch (FeignException.NotFound e){
              throw new ResourceNotFoundException(
                      "Store Not Found With id " + storeId +"----" + e.getMessage());
          }
        }

    public FixedAssetDto getAssetById(UUID tenantId, UUID itemId) {
        try{
            return fixedAssetServiceClient.getAssetById(tenantId,itemId);
        }catch (FeignException.NotFound e){
            throw new ResourceNotFoundException(
                    "Item Not Found with id" + itemId +"----" + e.getMessage());
        }

    }

    public ItemDto getItemById(UUID tenantId, UUID itemId) {
        try{
            return itemServiceClient.getItemById(tenantId, itemId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException(
                    "Item Not Found with id: " + itemId + " ---- " + e.getMessage());
        }

    }

    public EmployeeDto getEmployeeById(UUID tenantId, UUID employeeId){
        try{
            return employeeServiceClient.getEmployeeById(tenantId,employeeId);
        }catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Employee Not Found with id" + employeeId +
                    "-----" + e.getMessage());

        }
        }

        public BudgetDto getBudgetYearById(UUID tenantId, UUID budgetYearId) {
            try {
                return leaveServiceClient.getBudgetYearById(tenantId, budgetYearId);
            } catch (FeignException.NotFound e) {
                throw new ResourceNotFoundException("Budget Year not found with id: " + budgetYearId + " ---- " + e.getMessage());
            }
        }




    public FixedAssetDisposal getFixedAssetDisposalById(UUID tenantId, UUID id) {
        return fixedAssetDisposalRepository.findById(id)
                .filter(fad -> fad.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException("Fixed Asset Disposal not found with id: " + id));
    }

    public StockDisposal getStockDisposalById(UUID id, UUID tenantId) {
            return stockDisposalRepository.findById(id)
                    .filter(sd-> sd.getTenantId().equals(tenantId))
                    .orElseThrow(()-> new ResourceNotFoundException("Stock Disposal not found with id: " + id));
    }

    public DisposableAsset getDisposableAssetById(UUID tenantId, UUID disposableAssetId) {
            return disposableAssetRepository.findById(disposableAssetId)
                    .filter(da -> da.getTenantId().equals(tenantId))
                    .orElseThrow(() -> new ResourceNotFoundException("Disposable Asset not found with id: " + disposableAssetId));
    }
}

