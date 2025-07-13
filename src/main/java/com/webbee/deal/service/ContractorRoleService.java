package com.webbee.deal.service;

import com.webbee.deal.entity.ContractorRole;
import com.webbee.deal.repository.ContractorRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ContractorRoleService {

    private final ContractorRoleRepository contractorRoleRepository;

    public ContractorRoleService(ContractorRoleRepository contractorRoleRepository) {
        this.contractorRoleRepository = contractorRoleRepository;
    }

    @Transactional(readOnly = true)
    public List<ContractorRole> getAllActive() {
        return contractorRoleRepository.findAll(); // или findByIsActiveTrue()
    }
}
