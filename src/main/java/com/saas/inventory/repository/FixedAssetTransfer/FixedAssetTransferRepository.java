package com.saas.inventory.repository.FixedAssetTransfer;


import com.saas.inventory.model.FixedAssetTransfer.FixedAssetTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FixedAssetTransferRepository extends JpaRepository<FixedAssetTransfer, UUID> {
    @Query("SELECT t.transferNo FROM FixedAssetTransfer t " +
            "WHERE t.tenantId = :tenantId " +
            "AND EXTRACT(YEAR FROM t.createdAt) = :year " +
            "ORDER BY t.createdAt DESC")

    List<String> findRecentTransferNumbers(UUID tenantId, @Param("year") int currentYear);
    Page<FixedAssetTransfer> findByTenantId(UUID tenantId, Pageable pageable);
    Optional<FixedAssetTransfer> findByTenantIdAndTransferNo(@Param("tenantId") UUID tenantId, @Param("transferNo") String transferNumber);


}