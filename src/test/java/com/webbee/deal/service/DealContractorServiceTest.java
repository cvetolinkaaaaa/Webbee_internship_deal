package com.webbee.deal.service;

import com.webbee.deal.client.ContractorClient;
import com.webbee.deal.dto.ContractorDto;
import com.webbee.deal.dto.DealContractorDto;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.mapper.DealContractorMapper;
import com.webbee.deal.repository.DealContractorRepository;
import com.webbee.deal.repository.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealContractorServiceTest {

    @Mock private DealContractorRepository dealContractorRepository;
    @Mock private DealRepository dealRepository;
    @Mock private ContractorClient contractorClient;
    @Mock private DealContractorMapper dealContractorMapper;

    @InjectMocks
    private DealContractorService service;

    private final UUID DEAL_ID  = UUID.randomUUID();
    private final UUID DC_ID    = UUID.randomUUID();
    private final String CONTR_ID = "CONTR_123";

    @BeforeEach
    void init() { MockitoAnnotations.openMocks(this); }

    @Test
    void saveDealContractor_throws_whenContractorNotFound() {
        DealContractorDto dto = new DealContractorDto();
        dto.setContractorId(CONTR_ID);
        dto.setDeal(new Deal());
        dto.getDeal().setId(DEAL_ID);

        when(contractorClient.getContractor(CONTR_ID)).thenReturn(null);

        assertThatThrownBy(() -> service.saveDealContractor(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Contractor with id " + CONTR_ID);

        verify(dealContractorRepository, never()).save(any());
    }

    @Test
    void deleteDealContractor_setsInactive() {
        DealContractor dc = new DealContractor();
        dc.setId(DC_ID);
        dc.setIsActive(true);

        when(dealContractorRepository.findById(DC_ID)).thenReturn(Optional.of(dc));
        when(dealContractorRepository.save(dc)).thenReturn(dc);

        service.deleteDealContractor(DC_ID);

        assertThat(dc.getIsActive()).isFalse();
        assertThat(dc.getModifyDate()).isNotNull();

        verify(dealContractorRepository).save(dc);
    }
    @Test
    void deleteDealContractor_notFound_throws() {
        when(dealContractorRepository.findById(DC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteDealContractor(DC_ID))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
