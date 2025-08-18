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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractorToRoleServiceTest {

    @Mock
    private ContractorToRoleRepository contractorToRoleRepository;

    @Mock
    private ContractorRoleRepository contractorRoleRepository;

    @Mock
    private DealContractorRepository dealContractorRepository;

    @Mock
    private ContractorToRoleMapper contractorToRoleMapper;

    @InjectMocks
    private ContractorToRoleService contractorToRoleService;

    private UUID dealContractorId;
    private String roleId;
    private ContractorRole contractorRole;
    private DealContractor dealContractor;
    private ContractorToRole contractorToRole;
    private ContractorToRoleDto contractorToRoleDto;

    @BeforeEach
    void setUp() {
        dealContractorId = UUID.randomUUID();
        roleId = "BORROWER";
        
        contractorRole = new ContractorRole();
        contractorRole.setId(roleId);
        contractorRole.setName("Заемщик");
        contractorRole.setIsActive(true);
        
        dealContractor = new DealContractor();
        dealContractor.setId(dealContractorId);
        dealContractor.setName("Test Contractor");
        dealContractor.setIsActive(true);
        
        ContractorToRoleId id = new ContractorToRoleId();
        id.setContractorId(dealContractorId);
        id.setRoleId(roleId);
        
        contractorToRole = new ContractorToRole();
        contractorToRole.setId(id);
        contractorToRole.setDealContractor(dealContractor);
        contractorToRole.setRole(contractorRole);
        contractorToRole.setIsActive(true);
        
        contractorToRoleDto = new ContractorToRoleDto();
    }

    @Test
    void addRoleToDealContractor_Success() {
        when(dealContractorRepository.findById(dealContractorId)).thenReturn(Optional.of(dealContractor));
        when(contractorRoleRepository.findById(roleId)).thenReturn(Optional.of(contractorRole));
        when(contractorToRoleRepository.save(any(ContractorToRole.class))).thenReturn(contractorToRole);
        when(contractorToRoleMapper.toDto(contractorToRole)).thenReturn(contractorToRoleDto);

        ContractorToRoleDto result = contractorToRoleService.addRoleToDealContractor(dealContractorId, roleId);

        assertThat(result).isNotNull();
        verify(contractorToRoleRepository).save(any(ContractorToRole.class));
        verify(contractorToRoleMapper).toDto(contractorToRole);
    }

    @Test
    void addRoleToDealContractor_DealContractorNotFound() {
        when(dealContractorRepository.findById(dealContractorId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractorToRoleService.addRoleToDealContractor(dealContractorId, roleId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("DealContractor not found: " + dealContractorId);
    }

    @Test
    void addRoleToDealContractor_RoleNotFound() {
        when(dealContractorRepository.findById(dealContractorId)).thenReturn(Optional.of(dealContractor));
        when(contractorRoleRepository.findById(roleId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> contractorToRoleService.addRoleToDealContractor(dealContractorId, roleId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Role not found: " + roleId);
    }

}