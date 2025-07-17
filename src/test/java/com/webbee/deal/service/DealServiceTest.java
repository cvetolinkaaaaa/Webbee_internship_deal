package com.webbee.deal.service;

import com.webbee.deal.dto.DealDto;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.mapper.DealMapper;
import com.webbee.deal.repository.DealRepository;
import com.webbee.deal.repository.DealStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealServiceTest {

    private DealRepository dealRepository;
    private DealStatusRepository dealStatusRepository;
    private DealMapper dealMapper;

    private DealService dealService;

    @BeforeEach
    void setUp() {
        dealRepository = mock(DealRepository.class);
        dealStatusRepository = mock(DealStatusRepository.class);
        dealMapper = mock(DealMapper.class);

        dealService = new DealService(
                dealRepository,
                dealMapper,
                dealStatusRepository
        );
    }

    @Test
    void testSaveDeal_NewDeal() {
        DealDto dto = new DealDto();
        dto.setDescription("Test");
        dto.setAgreementNumber("A-1");

        Deal entity = new Deal();
        entity.setId(UUID.randomUUID());
        entity.setDescription("Test");
        entity.setAgreementNumber("A-1");

        DealStatus draftStatus = new DealStatus();
        draftStatus.setId("DRAFT");
        draftStatus.setName("Черновик");
        draftStatus.setIsActive(true);

        Deal savedEntity = new Deal();
        savedEntity.setId(entity.getId());
        savedEntity.setDescription("Test");
        savedEntity.setAgreementNumber("A-1");
        savedEntity.setStatus(draftStatus);

        DealDto savedDto = new DealDto();
        savedDto.setId(entity.getId());
        savedDto.setDescription("Test");
        savedDto.setAgreementNumber("A-1");

        when(dealMapper.toEntity(dto)).thenReturn(entity);
        when(dealStatusRepository.findById("DRAFT")).thenReturn(Optional.of(draftStatus));
        when(dealRepository.save(entity)).thenReturn(savedEntity);
        when(dealMapper.toDto(savedEntity)).thenReturn(savedDto);

        DealDto result = dealService.saveDeal(dto);

        assertThat(result.getDescription()).isEqualTo("Test");
        verify(dealStatusRepository).findById("DRAFT");
        verify(dealRepository).save(entity);
        verify(dealMapper).toDto(savedEntity);
    }


    @Test
    void testChangeDealStatus_Success() {
        UUID id = UUID.randomUUID();

        Deal entity = new Deal();
        entity.setId(id);
        entity.setDescription("Test");

        DealStatus status = new DealStatus();
        status.setId("ACTIVE");
        status.setName("Активна");
        status.setIsActive(true);

        DealDto dto = new DealDto();
        dto.setId(id);
        dto.setStatus(null);

        when(dealRepository.findById(id)).thenReturn(Optional.of(entity));
        when(dealStatusRepository.findById("ACTIVE")).thenReturn(Optional.of(status));
        when(dealRepository.save(any(Deal.class))).thenReturn(entity);
        when(dealMapper.toDto(entity)).thenReturn(dto);

        DealDto result = dealService.changeDealStatus(id, "ACTIVE");

        verify(dealStatusRepository).findById("ACTIVE");
        verify(dealRepository).save(entity);

        assertThat(entity.getStatus()).isEqualTo(status);
    }

}
