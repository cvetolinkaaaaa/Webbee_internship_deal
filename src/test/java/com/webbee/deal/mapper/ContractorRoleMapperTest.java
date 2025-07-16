package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorRoleDto;
import com.webbee.deal.entity.ContractorRole;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class ContractorRoleMapperTest {

    private final ContractorRoleMapper mapper = Mappers.getMapper(ContractorRoleMapper.class);

    @Test
    void testToDto() {
        ContractorRole entity = new ContractorRole();
        entity.setId("BORROWER");
        entity.setName("Заемщик");
        entity.setCategory("BORROWER");

        ContractorRoleDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(entity.getId());
        assertThat(dto.getName()).isEqualTo(entity.getName());
        assertThat(dto.getCategory()).isEqualTo(entity.getCategory());
    }

    @Test
    void testToEntity() {
        ContractorRoleDto dto = new ContractorRoleDto();
        dto.setId("WARRANTY");
        dto.setName("Поручитель");
        dto.setCategory("WARRANTY");

        ContractorRole entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(dto.getId());
        assertThat(entity.getName()).isEqualTo(dto.getName());
        assertThat(entity.getCategory()).isEqualTo(dto.getCategory());
    }
}
