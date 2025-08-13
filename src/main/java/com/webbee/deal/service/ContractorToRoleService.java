package com.webbee.deal.service;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.entity.ContractorRole;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.ContractorToRoleId;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.mapper.ContractorToRoleMapper;
import com.webbee.deal.repository.ContractorRoleRepository;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Сервис для управления связями между контрагентами и их ролями в рамках сделки.
 * @author Evseeva Tsvetolina
 */
@Service
public class ContractorToRoleService {

    private final DealContractorRepository dealContractorRepository;
    private final ContractorRoleRepository contractorRoleRepository;
    private final ContractorToRoleRepository contractorToRoleRepository;
    private final ContractorToRoleMapper contractorToRoleMapper;

    public ContractorToRoleService(
            DealContractorRepository dealContractorRepository,
            ContractorRoleRepository contractorRoleRepository,
            ContractorToRoleRepository contractorToRoleRepository,
            ContractorToRoleMapper contractorToRoleMapper
    ) {
        this.dealContractorRepository = dealContractorRepository;
        this.contractorRoleRepository = contractorRoleRepository;
        this.contractorToRoleRepository = contractorToRoleRepository;
        this.contractorToRoleMapper = contractorToRoleMapper;
    }

    /**
     * Добавляет роль контрагенту сделки.
     */
    @Transactional
    public ContractorToRoleDto addRoleToDealContractor(UUID dealContractorId, String roleId) {
        DealContractor dealContractor = dealContractorRepository.findById(dealContractorId)
                .orElseThrow(() -> new EntityNotFoundException("DealContractor not found: " + dealContractorId));
        ContractorRole role = contractorRoleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
        ContractorToRoleId id = new ContractorToRoleId();
        id.setContractorId(dealContractorId);
        id.setRoleId(roleId);
        ContractorToRole contractorToRole = new ContractorToRole();
        contractorToRole.setId(id);
        contractorToRole.setDealContractor(dealContractor);
        contractorToRole.setRole(role);
        contractorToRole.setIsActive(true);
        ContractorToRole saved = contractorToRoleRepository.save(contractorToRole);
        return contractorToRoleMapper.toDto(saved);
    }

    /**
     * Удаляет связь между контрагентом и ролью (устанавливает isActive=false).
     */
    @Transactional
    public void deleteContractorToRole(UUID contractorId, String roleId) {
        ContractorToRoleId id = new ContractorToRoleId(contractorId, roleId);
        ContractorToRole entity = contractorToRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Связь contractor_to_role не найдена"));

        if (!Boolean.FALSE.equals(entity.getIsActive())) {
            entity.setIsActive(false);
            contractorToRoleRepository.save(entity);
        }
    }

}
