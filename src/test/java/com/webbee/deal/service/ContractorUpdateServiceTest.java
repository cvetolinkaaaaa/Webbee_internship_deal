package com.webbee.deal.service;

import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.ContractorUpdateMessageDto;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContractorUpdateServiceTest {

    @Mock
    private DealContractorRepository dealContractorRepository;

    @Mock
    private ContractorToRoleRepository contractorToRoleRepository;

    @InjectMocks
    private ContractorUpdateService contractorUpdateService;

    private ContractorDto contractorDto;
    private DealContractor dealContractor;
    private Deal deal;

    @BeforeEach
    void setUp() {
        contractorDto = new ContractorDto();
        contractorDto.setId("CONTR123");
        contractorDto.setName("Test Contractor");
        contractorDto.setInn("1234567890");
        contractorDto.setIsActive(true);

        deal = new Deal();
        deal.setId(UUID.randomUUID());

        dealContractor = new DealContractor();
        dealContractor.setId(UUID.randomUUID());
        dealContractor.setContractorId("CONTR123");
        dealContractor.setName("Old Name");
        dealContractor.setInn("0987654321");
        dealContractor.setIsActive(true);
        dealContractor.setDeal(deal);
        dealContractor.setCreateDate(LocalDateTime.now().minusDays(1));
    }

    @Test
    void createContractor_Success() {
        contractorUpdateService.createContractor(contractorDto);

        verifyNoInteractions(dealContractorRepository);
        verifyNoInteractions(contractorToRoleRepository);
    }

    @Test
    void updateContractor_Success() {
        List<DealContractor> dealContractors = Arrays.asList(dealContractor);

        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(dealContractors);

        contractorUpdateService.updateContractor(contractorDto);

        verify(dealContractorRepository).saveAll(dealContractors);
        assertThat(dealContractor.getName()).isEqualTo("Test Contractor");
        assertThat(dealContractor.getInn()).isEqualTo("1234567890");
        assertThat(dealContractor.getModifyDate()).isNotNull();
    }

    @Test
    void updateContractor_NoActiveDeals() {
        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(Collections.emptyList());

        contractorUpdateService.updateContractor(contractorDto);

        verify(dealContractorRepository, never()).saveAll(anyList());
    }

    @Test
    void updateContractor_DeactivatesRoles() {
        contractorDto.setIsActive(false);
        List<DealContractor> dealContractors = Arrays.asList(dealContractor);
        
        ContractorToRole role = new ContractorToRole();
        role.setIsActive(true);
        List<ContractorToRole> roles = Arrays.asList(role);

        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(dealContractors);
        when(contractorToRoleRepository.findByIdContractorIdAndIsActiveTrue(dealContractor.getId()))
                .thenReturn(roles);

        contractorUpdateService.updateContractor(contractorDto);

        verify(contractorToRoleRepository).saveAll(roles);
        assertThat(role.getIsActive()).isFalse();
    }

    @Test
    void updateContractorInfo_OldFormat() {
        ContractorUpdateMessageDto updateDto = new ContractorUpdateMessageDto();
        updateDto.setContractorId("CONTR123");
        updateDto.setName("Updated Name");
        updateDto.setInn("9876543210");

        List<DealContractor> dealContractors = Arrays.asList(dealContractor);

        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(dealContractors);

        contractorUpdateService.updateContractorInfo(updateDto);

        verify(dealContractorRepository).saveAll(dealContractors);
        assertThat(dealContractor.getName()).isEqualTo("Updated Name");
        assertThat(dealContractor.getInn()).isEqualTo("9876543210");
    }

    @Test
    void findActiveContractorDeals() {
        List<DealContractor> expected = Arrays.asList(dealContractor);
        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(expected);

        List<DealContractor> result = contractorUpdateService.findActiveContractorDeals("CONTR123");

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void contractorExistsInDeals_True() {
        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(Arrays.asList(dealContractor));

        boolean result = contractorUpdateService.contractorExistsInDeals("CONTR123");

        assertThat(result).isTrue();
    }

    @Test
    void contractorExistsInDeals_False() {
        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(Collections.emptyList());

        boolean result = contractorUpdateService.contractorExistsInDeals("CONTR123");

        assertThat(result).isFalse();
    }

    @Test
    void countActiveContractorDeals() {
        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(Arrays.asList(dealContractor, new DealContractor()));

        long count = contractorUpdateService.countActiveContractorDeals("CONTR123");

        assertThat(count).isEqualTo(2);
    }

    @Test
    void updateContractor_NoChangesNeeded() {
        dealContractor.setName("Test Contractor");
        dealContractor.setInn("1234567890");
        dealContractor.setIsActive(true);

        List<DealContractor> dealContractors = Arrays.asList(dealContractor);

        when(dealContractorRepository.findByContractorIdAndIsActiveTrue("CONTR123"))
                .thenReturn(dealContractors);

        contractorUpdateService.updateContractor(contractorDto);

        verify(dealContractorRepository).saveAll(dealContractors);
        assertThat(dealContractor.getModifyDate()).isNull();
    }
}