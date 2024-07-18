package com.eyl.backend.mapper;

import com.eyl.backend.dto.DeskDTO;
import com.eyl.backend.entity.Desk;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeskMapper {
    DeskMapper INSTANCE = Mappers.getMapper(DeskMapper.class);

    DeskDTO mapToDeskDTO(Desk desk);

    Desk mapToDesk(DeskDTO deskDTO);
}
