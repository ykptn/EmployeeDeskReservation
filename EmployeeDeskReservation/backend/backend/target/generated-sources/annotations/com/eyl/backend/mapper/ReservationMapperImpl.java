package com.eyl.backend.mapper;

import com.eyl.backend.dto.ReservationDTO;
import com.eyl.backend.entity.Desk;
import com.eyl.backend.entity.Employee;
import com.eyl.backend.entity.Reservation;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T21:26:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public ReservationDTO mapToReservationDTO(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setEmpId( reservationEmployeeEmpId( reservation ) );
        reservationDTO.setDeskNo( reservationDeskDeskNo( reservation ) );
        reservationDTO.setResId( reservation.getResId() );
        reservationDTO.setDeskDate( reservation.getDeskDate() );

        return reservationDTO;
    }

    @Override
    public Reservation mapToReservation(ReservationDTO reservationDTO) {
        if ( reservationDTO == null ) {
            return null;
        }

        Reservation reservation = new Reservation();

        reservation.setEmployee( reservationDTOToEmployee( reservationDTO ) );
        reservation.setDesk( reservationDTOToDesk( reservationDTO ) );
        reservation.setResId( reservationDTO.getResId() );
        reservation.setDeskDate( reservationDTO.getDeskDate() );

        return reservation;
    }

    private Long reservationEmployeeEmpId(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Employee employee = reservation.getEmployee();
        if ( employee == null ) {
            return null;
        }
        Long empId = employee.getEmpId();
        if ( empId == null ) {
            return null;
        }
        return empId;
    }

    private Long reservationDeskDeskNo(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Desk desk = reservation.getDesk();
        if ( desk == null ) {
            return null;
        }
        Long deskNo = desk.getDeskNo();
        if ( deskNo == null ) {
            return null;
        }
        return deskNo;
    }

    protected Employee reservationDTOToEmployee(ReservationDTO reservationDTO) {
        if ( reservationDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmpId( reservationDTO.getEmpId() );

        return employee;
    }

    protected Desk reservationDTOToDesk(ReservationDTO reservationDTO) {
        if ( reservationDTO == null ) {
            return null;
        }

        Desk desk = new Desk();

        desk.setDeskNo( reservationDTO.getDeskNo() );

        return desk;
    }
}
