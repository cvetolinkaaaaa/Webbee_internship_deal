package com.webbee.deal.mapper;

import com.webbee.deal.dto.CurrencyDto;
import com.webbee.deal.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyMapperTest {

    @Autowired
    private CurrencyMapper mapper;

    @Test
    void testToDto() {
        Currency entity = new Currency();
        entity.setId("USD");
        entity.setName("US Dollar");

        CurrencyDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
    }

    @Test
    void testToEntity() {
        CurrencyDto dto = new CurrencyDto();
        dto.setId("EUR");
        dto.setName("Euro");

        Currency entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
    }

    @Test
    void testToDtoNull() {
        CurrencyDto dto = mapper.toDto(null);
        assertThat(dto).isNull();
    }

    @Test
    void testToEntityNull() {
        Currency entity = mapper.toEntity(null);
        assertThat(entity).isNull();
    }
}