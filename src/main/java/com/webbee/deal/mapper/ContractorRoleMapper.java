package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorRoleDto;
import com.webbee.deal.entity.ContractorRole;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractorRoleMapper {
    ContractorRoleDto toDto(ContractorRole entity);
    ContractorRole toEntity(ContractorRoleDto dto);
}
