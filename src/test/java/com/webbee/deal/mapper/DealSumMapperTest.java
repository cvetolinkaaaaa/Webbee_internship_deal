package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealSumDto;
import com.webbee.deal.entity.DealSum;
import com.webbee.deal.entity.Currency;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DealSumMapperTest {

    @Autowired
    private DealSumMapper mapper;

    @Test
    void testToDto() {
        DealSum entity = new DealSum();
        entity.setId(1L);
        entity.setValue(new BigDecimal("150000.50"));
        entity.setIsMain(true);
        entity.setIsActive(true);

        Currency currency = new Currency();
        currency.setId("RUB");
        currency.setName("Российский рубль");
        currency.setIsActive(true);
        entity.setCurrency(currency);

        DealSumDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getValue()).isEqualByComparingTo(entity.getValue());
        assertThat(dto.getIsMain()).isEqualTo(entity.getIsMain());
        assertThat(dto.getIsActive()).isEqualTo(entity.getIsActive());
        assertThat(dto.getCurrency()).isNotNull();
        assertThat(dto.getCurrency().getId()).isEqualTo("RUB");
        assertThat(dto.getCurrency().getName()).isEqualTo("Российский рубль");
    }

    @Test
    void testToEntity() {
        DealSumDto dto = new DealSumDto();
        dto.setValue(new BigDecimal("12345.67"));
        dto.setIsMain(false);
        dto.setIsActive(false);

        DealSum entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getValue()).isEqualByComparingTo(dto.getValue());
        assertThat(entity.getIsMain()).isEqualTo(dto.getIsMain());
        assertThat(entity.getIsActive()).isEqualTo(dto.getIsActive());
    }
}
