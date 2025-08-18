
package com.webbee.deal.service;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.entity.*;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealSumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DealDetailsServiceTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealSumRepository dealSumRepository;

    @Mock
    private DealContractorRepository dealContractorRepository;

    @Mock
    private ContractorToRoleRepository contractorToRoleRepository;

    @InjectMocks
    private DealDetailsService dealDetailsService;

    private UUID dealId;
    private Deal deal;
    private DealSum dealSum;
    private DealContractor dealContractor;
    private ContractorToRole contractorToRole;

    @BeforeEach
    void setUp() {
        dealId = UUID.randomUUID();
        
        DealType dealType = new DealType();
        dealType.setId("CREDIT");
        dealType.setName("Кредитная сделка");
        
        DealStatus dealStatus = new DealStatus();
        dealStatus.setId("DRAFT");
        dealStatus.setName("Черновик");
        
        Currency currency = new Currency();
        currency.setId("RUB");
        currency.setName("Рубль");
        
        deal = new Deal();
        deal.setId(dealId);
        deal.setDescription("Test deal");
        deal.setAgreementNumber("001");
        deal.setAgreementDate(LocalDate.now());
        deal.setAgreementStartDt(LocalDateTime.now());
        deal.setAvailabilityDate(LocalDate.now());
        deal.setCloseDt(LocalDateTime.now());
        deal.setType(dealType);
        deal.setStatus(dealStatus);
        
        dealSum = new DealSum();
        dealSum.setValue(BigDecimal.valueOf(100000));
        dealSum.setCurrency(currency);
        dealSum.setIsMain(true);
        
        ContractorRole contractorRole = new ContractorRole();
        contractorRole.setId("BORROWER");
        contractorRole.setName("Заемщик");
        contractorRole.setCategory("MAIN");
        
        dealContractor = new DealContractor();
        dealContractor.setId(UUID.randomUUID());
        dealContractor.setContractorId("123456789012");
        dealContractor.setName("Test Contractor");
        dealContractor.setIsMain(true);
        
        contractorToRole = new ContractorToRole();
        contractorToRole.setRole(contractorRole);
    }

    @Test
    void getDealDetails_Success() {
        when(dealRepository.findById(dealId)).thenReturn(Optional.of(deal));
        when(dealSumRepository.findByDealIdAndIsMainTrue(dealId)).thenReturn(Optional.of(dealSum));
        when(dealContractorRepository.findByDealIdAndIsActiveTrue(dealId)).thenReturn(List.of(dealContractor));
        when(contractorToRoleRepository.findByIdContractorIdAndIsActiveTrue(dealContractor.getId()))
                .thenReturn(List.of(contractorToRole));

        DealDetailsDto result = dealDetailsService.getDealDetails(dealId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(dealId);
        assertThat(result.getDescription()).isEqualTo("Test deal");
        assertThat(result.getAgreementNumber()).isEqualTo("001");
        assertThat(result.getType()).isNotNull();
        assertThat(result.getType().getId()).isEqualTo("CREDIT");
        assertThat(result.getStatus()).isNotNull();
        assertThat(result.getStatus().getId()).isEqualTo("DRAFT");
        assertThat(result.getSum()).isNotNull();
        assertThat(result.getSum().getValue()).isEqualTo("100000");
        assertThat(result.getSum().getCurrency()).isEqualTo("RUB");
        assertThat(result.getContractors()).hasSize(1);
        assertThat(result.getContractors().get(0).getName()).isEqualTo("Test Contractor");
        assertThat(result.getContractors().get(0).getRoles()).hasSize(1);
    }

    @Test
    void getDealDetails_DealNotFound() {
        when(dealRepository.findById(dealId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dealDetailsService.getDealDetails(dealId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Deal not found: " + dealId);
    }

    @Test
    void getDealDetails_NoMainSum() {
        deal.setType(null);
        deal.setStatus(null);
        
        when(dealRepository.findById(dealId)).thenReturn(Optional.of(deal));
        when(dealSumRepository.findByDealIdAndIsMainTrue(dealId)).thenReturn(Optional.empty());
        when(dealContractorRepository.findByDealIdAndIsActiveTrue(dealId)).thenReturn(List.of());
        
        DealDetailsDto result = dealDetailsService.getDealDetails(dealId);
        
        assertThat(result).isNotNull();
        assertThat(result.getType()).isNull();
        assertThat(result.getStatus()).isNull();
        assertThat(result.getSum()).isNull();
        assertThat(result.getContractors()).isEmpty();
    }

    @Test
    void getDealDetails_SumWithoutCurrency() {
        dealSum.setCurrency(null);
        
        when(dealRepository.findById(dealId)).thenReturn(Optional.of(deal));
        when(dealSumRepository.findByDealIdAndIsMainTrue(dealId)).thenReturn(Optional.of(dealSum));
        when(dealContractorRepository.findByDealIdAndIsActiveTrue(dealId)).thenReturn(List.of());
        
        DealDetailsDto result = dealDetailsService.getDealDetails(dealId);
        
        assertThat(result.getSum()).isNotNull();
        assertThat(result.getSum().getCurrency()).isNull();
    }
}