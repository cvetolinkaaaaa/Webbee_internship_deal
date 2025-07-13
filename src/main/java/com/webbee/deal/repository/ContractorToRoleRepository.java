package com.webbee.deal.repository;

import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.ContractorToRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ContractorToRoleRepository extends JpaRepository<ContractorToRole, ContractorToRoleId> {
    List<ContractorToRole> findByContractorId(UUID contractorId);
    List<ContractorToRole> findByRoleId(String roleId);
}
