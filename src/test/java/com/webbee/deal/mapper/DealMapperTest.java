package com.webbee.deal.mapper;

import org.junit.jupiter.api.Test;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.dto.DealTypeDto;
import com.webbee.deal.entity.Deal;
import com.webbee.deal.entity.DealType;
import com.webbee.deal.entity.DealStatus;
import com.webbee.deal.entity.DealContractor;
import com.webbee.deal.mapper.DealMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DealMapperTest {

    @Autowired
    private DealMapper mapper;
    @Test
    void testToDto() {
        Deal deal = new Deal();
        deal.setId(UUID.randomUUID());
        deal.setDescription("Test Deal");
        deal.setAgreementNumber("AGR-123");
        deal.setAgreementDate(LocalDate.of(2024, 7, 10));
        deal.setAgreementStartDt(LocalDateTime.of(2024, 7, 12, 10, 0));
        deal.setAvailabilityDate(LocalDate.of(2024, 12, 31));
        deal.setCreateDate(LocalDateTime.now());
        deal.setIsActive(true);

        DealDto dto = mapper.toDto(deal);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(deal.getId());
        assertThat(dto.getDescription()).isEqualTo(deal.getDescription());
        assertThat(dto.getAgreementNumber()).isEqualTo(deal.getAgreementNumber());
        assertThat(dto.getAgreementDate()).isEqualTo(deal.getAgreementDate());
        assertThat(dto.getAgreementStartDt()).isEqualTo(deal.getAgreementStartDt());
        assertThat(dto.getAvailabilityDate()).isEqualTo(deal.getAvailabilityDate());
        assertThat(dto.getIsActive()).isEqualTo(deal.getIsActive());
    }

    @Test
    void testToEntity() {
        DealDto dto = new DealDto();
        dto.setId(UUID.randomUUID());
        dto.setDescription("Test Deal DTO");
        dto.setAgreementNumber("AGR-456");
        dto.setAgreementDate(LocalDate.of(2024, 8, 10));
        dto.setAgreementStartDt(LocalDateTime.of(2024, 8, 12, 10, 0));
        dto.setAvailabilityDate(LocalDate.of(2024, 12, 31));
        dto.setIsActive(false);

        Deal deal = mapper.toEntity(dto);

        assertThat(deal).isNotNull();
        assertThat(deal.getId()).isEqualTo(dto.getId());
        assertThat(deal.getDescription()).isEqualTo(dto.getDescription());
        assertThat(deal.getAgreementNumber()).isEqualTo(dto.getAgreementNumber());
        assertThat(deal.getAgreementDate()).isEqualTo(dto.getAgreementDate());
        assertThat(deal.getAgreementStartDt()).isEqualTo(dto.getAgreementStartDt());
        assertThat(deal.getAvailabilityDate()).isEqualTo(dto.getAvailabilityDate());
        assertThat(deal.getIsActive()).isEqualTo(dto.getIsActive());
    }
}
