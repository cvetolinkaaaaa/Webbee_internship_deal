package com.webbee.deal.repository;

import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.ContractorToRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы со связями между контрагентами и ролями.
 */
public interface ContractorToRoleRepository extends JpaRepository<ContractorToRole, ContractorToRoleId> {

    /**
     * Находит все активные связи между контрагентом и ролями по идентификатору контрагента.
     */
    List<ContractorToRole> findByIdContractorIdAndIsActiveTrue(UUID contractorId);

}
