package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.entity.ContractorToRoleId;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContractorToRoleIdMapperTest {

    private final ContractorToRoleIdMapper mapper = Mappers.getMapper(ContractorToRoleIdMapper.class);

    @Test
    void testToDto() {
        UUID contractorId = UUID.randomUUID();
        ContractorToRoleId entity = new ContractorToRoleId(contractorId, "BORROWER");
        ContractorToRoleIdDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getContractorId()).isEqualTo(contractorId);
        assertThat(dto.getRoleId()).isEqualTo("BORROWER");
    }

    @Test
    void testToEntity() {
        UUID contractorId = UUID.randomUUID();
        ContractorToRoleIdDto dto = new ContractorToRoleIdDto();
        dto.setContractorId(contractorId);
        dto.setRoleId("WARRANTY");

        ContractorToRoleId entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getContractorId()).isEqualTo(contractorId);
        assertThat(entity.getRoleId()).isEqualTo("WARRANTY");
    }
}
