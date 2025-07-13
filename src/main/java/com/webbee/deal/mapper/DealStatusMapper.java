package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealStatusDto;
import com.webbee.deal.entity.DealStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DealStatusMapper {
    DealStatusDto toDto(DealStatus entity);
    DealStatus toEntity(DealStatusDto dto);
}
