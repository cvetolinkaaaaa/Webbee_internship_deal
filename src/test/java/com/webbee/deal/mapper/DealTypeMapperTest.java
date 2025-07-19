package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealTypeDto;
import com.webbee.deal.entity.DealType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class DealTypeMapperTest {

    private final DealTypeMapper mapper = Mappers.getMapper(DealTypeMapper.class);

    @Test
    void testToDto() {
        DealType entity = new DealType();
        entity.setId("CREDIT");
        entity.setName("Кредитная сделка");
        entity.setIsActive(true);

        DealTypeDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo("CREDIT");
        assertThat(dto.getName()).isEqualTo("Кредитная сделка");
        assertThat(dto.getIsActive()).isTrue();
    }

    @Test
    void testToEntity() {
        DealTypeDto dto = new DealTypeDto();
        dto.setId("LEASING");
        dto.setName("Лизинговая сделка");
        dto.setIsActive(false);

        DealType entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo("LEASING");
        assertThat(entity.getName()).isEqualTo("Лизинговая сделка");
        assertThat(entity.getIsActive()).isFalse();
    }
}
