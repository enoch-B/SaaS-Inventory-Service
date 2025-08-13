package com.saas.inventory.repository.StockDisposal;

import com.saas.inventory.model.StockDisposal.StockDisposalDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockDisposalDetailRepository extends JpaRepository<StockDisposalDetail, UUID> {
    // Additional query methods can be defined here if needed
}
