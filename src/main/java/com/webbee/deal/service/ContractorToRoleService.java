package com.webbee.deal.service;

import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.ContractorToRoleId;
import com.webbee.deal.repository.ContractorToRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ContractorToRoleService {

    private final ContractorToRoleRepository contractorToRoleRepository;

    public ContractorToRoleService(ContractorToRoleRepository contractorToRoleRepository) {
        this.contractorToRoleRepository = contractorToRoleRepository;
    }

    @Transactional
    public ContractorToRole addRoleToContractor(ContractorToRole entity) {
        entity.setIsActive(true);
        return contractorToRoleRepository.save(entity);
    }

//    @Transactional
//    public void deleteRoleFromContractor(UUID contractorId, String roleId) {
//        ContractorToRoleId id = new ContractorToRoleId(contractorId, roleId);
//        ContractorToRole ctr = contractorToRoleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("ContractorToRole not found"));
//        ctr.setIsActive(false);
//        contractorToRoleRepository.save(ctr);
//    }

    @Transactional(readOnly = true)
    public List<ContractorToRole> getRolesByContractor(UUID contractorId) {
        return contractorToRoleRepository.findByContractorId(contractorId);
    }
}
