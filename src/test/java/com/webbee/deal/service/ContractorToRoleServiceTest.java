
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContractorToRoleServiceTest {

    @Mock
    private DealContractorRepository dealContractorRepository;
    
    @Mock
    private ContractorRoleRepository contractorRoleRepository;
    
    @Mock
    private ContractorToRoleRepository contractorToRoleRepository;
    
    @Mock
    private ContractorToRoleMapper contractorToRoleMapper;

    @InjectMocks
    private ContractorToRoleService service;

    private final UUID DEAL_CONTRACTOR_ID = UUID.randomUUID();
    private final String ROLE_ID = "BORROWER";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Успешно добавляет роль контрагенту сделки")
    void addRoleToDealContractor_WithValidData_ReturnsDto() {

        DealContractor dealContractor = new DealContractor();
        dealContractor.setId(DEAL_CONTRACTOR_ID);
        ContractorRole role = new ContractorRole();
        role.setId(ROLE_ID);
        ContractorToRole savedEntity = new ContractorToRole();
        ContractorToRoleDto expectedDto = new ContractorToRoleDto();
        when(dealContractorRepository.findById(DEAL_CONTRACTOR_ID)).thenReturn(Optional.of(dealContractor));
        when(contractorRoleRepository.findById(ROLE_ID)).thenReturn(Optional.of(role));
        when(contractorToRoleRepository.save(any(ContractorToRole.class))).thenReturn(savedEntity);
        when(contractorToRoleMapper.toDto(savedEntity)).thenReturn(expectedDto);
        ContractorToRoleDto result = service.addRoleToDealContractor(DEAL_CONTRACTOR_ID, ROLE_ID);
        assertThat(result).isEqualTo(expectedDto);
        verify(contractorToRoleRepository).save(any(ContractorToRole.class));

    }

    @Test
    @DisplayName("Выбрасывает исключение при отсутствии контрагента сделки")
    void addRoleToDealContractor_WithNonExistentDealContractor_ThrowsException() {

        when(dealContractorRepository.findById(DEAL_CONTRACTOR_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.addRoleToDealContractor(DEAL_CONTRACTOR_ID, ROLE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("DealContractor not found");
        verify(contractorToRoleRepository, never()).save(any());

    }

    @Test
    @DisplayName("Выбрасывает исключение при отсутствии роли")
    void addRoleToDealContractor_WithNonExistentRole_ThrowsException() {

        DealContractor dealContractor = new DealContractor();
        dealContractor.setId(DEAL_CONTRACTOR_ID);
        when(dealContractorRepository.findById(DEAL_CONTRACTOR_ID)).thenReturn(Optional.of(dealContractor));
        when(contractorRoleRepository.findById(ROLE_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.addRoleToDealContractor(DEAL_CONTRACTOR_ID, ROLE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Role not found");
        verify(contractorToRoleRepository, never()).save(any());

    }

    @Test
    @DisplayName("Выбрасывает исключение при отсутствии связи")
    void deleteContractorToRole_WithNonExistentRelation_ThrowsException() {

        ContractorToRoleId id = new ContractorToRoleId(DEAL_CONTRACTOR_ID, ROLE_ID);
        when(contractorToRoleRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.deleteContractorToRole(DEAL_CONTRACTOR_ID, ROLE_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Связь contractor_to_role не найдена");

    }

}