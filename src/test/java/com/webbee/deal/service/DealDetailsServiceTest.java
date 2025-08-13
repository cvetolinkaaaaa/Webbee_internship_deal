package com.webbee.deal.service;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.entity.ContractorRole;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.Currency;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.entity.DealSum;
import com.webbee.deal.entity.DealType;
import com.webbee.deal.repository.ContractorToRoleRepository;
import com.webbee.deal.repository.DealContractorRepository;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealSumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

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
    private DealDetailsService service;

    private final UUID DEAL_ID = UUID.randomUUID();
    private final UUID CONTRACTOR_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Успешно возвращает детальную информацию о сделке")
    void getDealDetails_WithValidData_ReturnsFullDetails() {

        Deal deal = createTestDeal();
        DealSum mainSum = createTestDealSum();
        DealContractor contractor = createTestDealContractor();
        ContractorToRole contractorRole = createTestContractorToRole();
        when(dealRepository.findById(DEAL_ID)).thenReturn(Optional.of(deal));
        when(dealSumRepository.findByDealIdAndIsMainTrue(DEAL_ID)).thenReturn(Optional.of(mainSum));
        when(dealContractorRepository.findByDealIdAndIsActiveTrue(DEAL_ID)).thenReturn(List.of(contractor));
        when(contractorToRoleRepository.findByIdContractorIdAndIsActiveTrue(CONTRACTOR_ID))
                .thenReturn(List.of(contractorRole));
        DealDetailsDto result = service.getDealDetails(DEAL_ID);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(DEAL_ID);
        assertThat(result.getDescription()).isEqualTo("Test Deal");
        assertThat(result.getType().getId()).isEqualTo("CREDIT");
        assertThat(result.getType().getName()).isEqualTo("Кредит");
        assertThat(result.getStatus().getId()).isEqualTo("ACTIVE");
        assertThat(result.getStatus().getName()).isEqualTo("Активный");
        assertThat(result.getSum().getValue()).isEqualTo("100000");
        assertThat(result.getSum().getCurrency()).isEqualTo("RUB");
        assertThat(result.getContractors()).hasSize(1);
        assertThat(result.getContractors().get(0).getName()).isEqualTo("Test Contractor");
        assertThat(result.getContractors().get(0).getRoles()).hasSize(1);
        assertThat(result.getContractors().get(0).getRoles().get(0).getId()).isEqualTo("BORROWER");

    }

    @Test
    @DisplayName("Успешно возвращает данные сделки без суммы")
    void getDealDetails_WithoutMainSum_ReturnsDealWithoutSum() {

        Deal deal = createTestDeal();
        when(dealRepository.findById(DEAL_ID)).thenReturn(Optional.of(deal));
        when(dealSumRepository.findByDealIdAndIsMainTrue(DEAL_ID)).thenReturn(Optional.empty());
        when(dealContractorRepository.findByDealIdAndIsActiveTrue(DEAL_ID)).thenReturn(Collections.emptyList());
        DealDetailsDto result = service.getDealDetails(DEAL_ID);
        assertThat(result).isNotNull();
        assertThat(result.getSum()).isNull();
        assertThat(result.getContractors()).isEmpty();

    }

    @Test
    @DisplayName("Выбрасывает исключение при отсутствии сделки")
    void getDealDetails_WithNonExistentDeal_ThrowsException() {

        when(dealRepository.findById(DEAL_ID)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getDealDetails(DEAL_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Deal not found");

    }

    private Deal createTestDeal() {

        Deal deal = new Deal();
        deal.setId(DEAL_ID);
        deal.setDescription("Test Deal");
        deal.setAgreementNumber("AGR-001");
        deal.setAgreementDate(LocalDate.now());
        DealType type = new DealType();
        type.setId("CREDIT");
        type.setName("Кредит");
        deal.setType(type);
        DealStatus status = new DealStatus();
        status.setId("ACTIVE");
        status.setName("Активный");
        deal.setStatus(status);
        return deal;

    }

    private DealSum createTestDealSum() {

        DealSum sum = new DealSum();
        sum.setValue(new BigDecimal("100000"));
        Currency currency = new Currency();
        currency.setId("RUB");
        sum.setCurrency(currency);
        return sum;

    }

    private DealContractor createTestDealContractor() {

        DealContractor contractor = new DealContractor();
        contractor.setId(CONTRACTOR_ID);
        contractor.setContractorId("CONTR-001");
        contractor.setName("Test Contractor");
        contractor.setIsMain(true);
        return contractor;

    }

    private ContractorToRole createTestContractorToRole() {

        ContractorToRole contractorToRole = new ContractorToRole();
        ContractorRole role = new ContractorRole();
        role.setId("BORROWER");
        role.setName("Заемщик");
        role.setCategory("BORROWER");
        contractorToRole.setRole(role);
        return contractorToRole;

    }

}