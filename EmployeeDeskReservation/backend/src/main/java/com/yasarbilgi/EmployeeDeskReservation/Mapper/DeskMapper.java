package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DeskDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Desk;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeskMapper {

    DeskMapper INSTANCE = Mappers.getMapper(DeskMapper.class);

    DeskDTO mapToDeskDTO(Desk desk);

    Desk mapToDesk(DeskDTO deskDTO);
}