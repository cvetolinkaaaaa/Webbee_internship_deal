package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealStatusDto;
import com.webbee.deal.entity.DealStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class DealStatusMapperTest {

    private final DealStatusMapper mapper = Mappers.getMapper(DealStatusMapper.class);

    @Test
    void testToDto() {
        DealStatus entity = new DealStatus();
        entity.setId("DRAFT");
        entity.setName("Черновик");
        entity.setIsActive(true);

        DealStatusDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo("DRAFT");
        assertThat(dto.getName()).isEqualTo("Черновик");
        assertThat(dto.getIsActive()).isTrue();
    }

    @Test
    void testToEntity() {
        DealStatusDto dto = new DealStatusDto();
        dto.setId("APPROVED");
        dto.setName("Утверждён");
        dto.setIsActive(false);

        DealStatus entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo("APPROVED");
        assertThat(entity.getName()).isEqualTo("Утверждён");
        assertThat(entity.getIsActive()).isFalse();
    }

}
