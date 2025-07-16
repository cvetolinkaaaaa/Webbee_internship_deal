package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleDto;
import com.webbee.deal.dto.ContractorRoleDto;
import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.entity.ContractorRole;
import com.webbee.deal.entity.ContractorToRole;
import com.webbee.deal.entity.ContractorToRoleId;
import com.webbee.deal.entity.DealContractor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ContractorToRoleMapperTest {

    @Autowired
    private ContractorToRoleMapper mapper;

    @Test
    void testToDto() {
        UUID contractorId = UUID.randomUUID();

        ContractorToRoleId id = new ContractorToRoleId(contractorId, "BORROWER");
        ContractorRole role = new ContractorRole("BORROWER", "Заемщик", "BORROWER", true);
        DealContractor dealContractor = new DealContractor();
        dealContractor.setId(contractorId);

        ContractorToRole entity = new ContractorToRole();
        entity.setId(id);
        entity.setRole(role);
        entity.setDealContractor(dealContractor);
        entity.setIsActive(true);

        ContractorToRoleDto dto = mapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId().getContractorId()).isEqualTo(contractorId);
        assertThat(dto.getId().getRoleId()).isEqualTo("BORROWER");
        assertThat(dto.getRole().getId()).isEqualTo("BORROWER");
        assertThat(dto.getIsActive()).isTrue();
    }

    @Test
    void testToEntity() {
        UUID contractorId = UUID.randomUUID();

        ContractorToRoleIdDto idDto = new ContractorToRoleIdDto();
        idDto.setContractorId(contractorId);
        idDto.setRoleId("WARRANTY");

        ContractorRoleDto roleDto = new ContractorRoleDto();
        roleDto.setId("WARRANTY");
        roleDto.setName("Поручитель");
        roleDto.setCategory("WARRANTY");

        ContractorToRoleDto dto = new ContractorToRoleDto();
        dto.setId(idDto);
        dto.setRole(roleDto);
        dto.setIsActive(false);

        ContractorToRole entity = mapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getId().getContractorId()).isEqualTo(contractorId);
        assertThat(entity.getId().getRoleId()).isEqualTo("WARRANTY");
        assertThat(entity.getRole().getId()).isEqualTo("WARRANTY");
        assertThat(entity.getIsActive()).isFalse();
    }
}
