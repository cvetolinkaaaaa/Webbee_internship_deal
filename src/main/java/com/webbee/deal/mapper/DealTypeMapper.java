package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealTypeDto;
import com.webbee.deal.entity.DealType;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DealTypeMapper {
    DealTypeDto toDto(DealType entity);
    DealType toEntity(DealTypeDto dto);
}
