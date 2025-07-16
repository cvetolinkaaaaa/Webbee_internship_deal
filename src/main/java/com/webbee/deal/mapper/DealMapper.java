package com.webbee.deal.mapper;

import com.webbee.deal.dto.DealDetailsDto;
import com.webbee.deal.dto.DealDto;
import com.webbee.deal.entity.Deal;
import org.mapstruct.*;
import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {
        DealTypeMapper.class,
        DealStatusMapper.class,
        DealSumMapper.class,
        DealContractorMapper.class
    }
)
public interface DealMapper {

    DealDto toDto(Deal entity);

    Deal toEntity(DealDto dto);

    List<DealDto> toDtoList(List<Deal> entityList);

    List<Deal> toEntityList(List<DealDto> dtoList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "createUserId", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(DealDto dto, @MappingTarget Deal entity);

    DealDetailsDto toDetailsDto(Deal deal);
}
