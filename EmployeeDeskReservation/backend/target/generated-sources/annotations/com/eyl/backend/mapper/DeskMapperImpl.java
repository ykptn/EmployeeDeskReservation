package com.eyl.backend.mapper;

import com.eyl.backend.dto.DeskDTO;
import com.eyl.backend.entity.Desk;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-11T01:35:36+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
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
        deskDTO.setStatus( desk.getStatus() );

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
        desk.setStatus( deskDTO.getStatus() );

        return desk;
    }
}
