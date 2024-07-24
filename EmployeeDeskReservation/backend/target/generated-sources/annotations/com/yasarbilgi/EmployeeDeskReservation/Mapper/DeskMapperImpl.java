package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DeskDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Desk;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T14:46:10+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class DeskMapperImpl implements DeskMapper {

    @Override
    public DeskDTO mapToDeskDTO(Desk desk) {
        if ( desk == null ) {
            return null;
        }

        DeskDTO deskDTO = new DeskDTO();

        deskDTO.setDeskNo( desk.getDeskNo() );
        deskDTO.setRoom( desk.getRoom() );
        Set<LocalDate> set = desk.getUnavailableDates();
        if ( set != null ) {
            deskDTO.setUnavailableDates( new HashSet<LocalDate>( set ) );
        }

        return deskDTO;
    }

    @Override
    public Desk mapToDesk(DeskDTO deskDTO) {
        if ( deskDTO == null ) {
            return null;
        }

        Desk desk = new Desk();

        desk.setDeskNo( deskDTO.getDeskNo() );
        desk.setRoom( deskDTO.getRoom() );
        Set<LocalDate> set = deskDTO.getUnavailableDates();
        if ( set != null ) {
            desk.setUnavailableDates( new HashSet<LocalDate>( set ) );
        }

        return desk;
    }
}