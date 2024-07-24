package com.yasarbilgi.EmployeeDeskReservation.Service.Implementation;

import com.yasarbilgi.EmployeeDeskReservation.DTO.ReservationDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Desk;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Employee;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Reservation;
import com.yasarbilgi.EmployeeDeskReservation.Exception.ResourceNotFoundException;
import com.yasarbilgi.EmployeeDeskReservation.Mapper.ReservationMapper;
import com.yasarbilgi.EmployeeDeskReservation.Repository.DeskRepository;
import com.yasarbilgi.EmployeeDeskReservation.Repository.EmployeeRepository;
import com.yasarbilgi.EmployeeDeskReservation.Repository.ReservationRepository;
import com.yasarbilgi.EmployeeDeskReservation.Service.Interface.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final EmployeeRepository employeeRepository;
    private final DeskRepository deskRepository;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = ReservationMapper.INSTANCE.mapToReservation(reservationDTO);

        if (reservationDTO.getEmpId() != null) {
            reservation.setEmployee(employeeRepository.findById(reservationDTO.getEmpId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
        } else {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        if (reservationDTO.getDeskNo() != null) {
            Desk desk = deskRepository.findById(reservationDTO.getDeskNo())
                    .orElseThrow(() -> new RuntimeException("Desk not found"));
            if (desk.getUnavailableDates().contains(reservationDTO.getDeskDate())) {
                throw new IllegalArgumentException("Desk is already reserved for " + reservationDTO.getDeskDate());
            }

            LocalDate today = LocalDate.now();
            if (reservationDTO.getDeskDate().isBefore(today)) {
                throw new IllegalArgumentException("Cannot reserve for past dates");
            }

            if (reservation.getEmployee().getRole() == RoleConstants.USER) {
                if (checkExistingReservation(reservationDTO)) {
                    throw new IllegalArgumentException("Employee already has a reservation for the day");
                }
            }

            desk.getUnavailableDates().add(reservationDTO.getDeskDate());
            reservation.setDesk(desk);

        } else {
            throw new IllegalArgumentException("Desk ID cannot be null");
        }

        reservation.setResDate(LocalDate.now());
        Reservation savedReservation = reservationRepository.save(reservation);
        return ReservationMapper.INSTANCE.mapToReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));
        return ReservationMapper.INSTANCE.mapToReservationDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper.INSTANCE::mapToReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationDTO updateReservation(Long reservationId, ReservationDTO updatedReservationDTO) {
        Reservation existingReservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        deleteReservation(reservationId);
        return createReservation(updatedReservationDTO);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        if (reservation.getDesk() != null && reservation.getDeskDate() != null) {
            reservation.getDesk().getUnavailableDates().remove(reservation.getDeskDate());
        }

        reservationRepository.delete(reservation);
    }

    public List<ReservationDTO> getReservationHistoryForLastMonth(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Reservation> reservations = reservationRepository.findByEmployeeAndResDateAfter(employee, oneMonthAgo);

        return reservations.stream()
                .map(ReservationMapper.INSTANCE::mapToReservationDTO)
                .collect(Collectors.toList());
    }

    private boolean checkExistingReservation(ReservationDTO reservationDTO) {
        Employee employee = employeeRepository.findById(reservationDTO.getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + reservationDTO.getEmpId()));

        LocalDate deskDate = reservationDTO.getDeskDate();
        List<Reservation> existingReservations = reservationRepository.findByEmployeeAndDeskDate(employee, deskDate);

        return !existingReservations.isEmpty();
    }
}