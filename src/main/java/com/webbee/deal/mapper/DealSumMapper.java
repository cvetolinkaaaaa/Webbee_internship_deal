package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealSumDto;
import com.webbee.deal.entity.DealSum;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface DealSumMapper {
    DealSumDto toDto(DealSum entity);
    DealSum toEntity(DealSumDto dto);

    List<DealSumDto> toDtoList(List<DealSum> entityList);
    List<DealSum> toEntityList(List<DealSumDto> dtoList);
}
