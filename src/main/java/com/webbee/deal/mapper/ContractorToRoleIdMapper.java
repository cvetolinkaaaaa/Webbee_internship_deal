package com.webbee.deal.mapper;

import com.webbee.deal.dto.ContractorToRoleIdDto;
import com.webbee.deal.entity.ContractorToRoleId;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContractorToRoleIdMapper {
    ContractorToRoleIdDto toDto(ContractorToRoleId entity);
    ContractorToRoleId toEntity(ContractorToRoleIdDto dto);
}
