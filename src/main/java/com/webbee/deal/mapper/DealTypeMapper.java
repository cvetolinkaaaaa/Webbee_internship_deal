package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealTypeDto;
import com.webbee.deal.entity.DealType;
import org.mapstruct.Mapper;

/**
 * Маппер для преобразования между сущностью DealType и DTO DealTypeDto.
 * @author Evseeva Tsvetolina
 */
@Mapper(componentModel = "spring")
public interface DealTypeMapper {

    /**
     * Преобразует сущность DealType в DTO DealTypeDto.
     */
    DealTypeDto toDto(DealType entity);


    /**
     * Преобразует DTO DealTypeDto в DealType.
     */
    DealType toEntity(DealTypeDto dto);

}
