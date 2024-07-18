package com.eyl.backend.service.serviceImplementations;

import com.eyl.backend.dto.ReservationDTO;
import com.eyl.backend.entity.Desk;
import com.eyl.backend.entity.Employee;
import com.eyl.backend.entity.Reservation;
import com.eyl.backend.enums.RoleEnum;
import com.eyl.backend.exception.ResourceNotFoundException;
import com.eyl.backend.mapper.ReservationMapper;
import com.eyl.backend.repository.DeskRepository;
import com.eyl.backend.repository.EmployeeRepository;
import com.eyl.backend.repository.ReservationRepository;
import com.eyl.backend.service.serviceInterfaces.IReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {

    private final ReservationRepository repo;
    private final EmployeeRepository employeeRepository;
    private final DeskRepository deskRepository;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = ReservationMapper.INSTANCE.mapToReservation(reservationDTO);

        // Validate and set Employee
        if (reservationDTO.getEmpId() != null) {
            reservation.setEmployee(employeeRepository.findById(reservationDTO.getEmpId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
        } else {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        // Validate and set Desk
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

            // If the employee is a worker, check if they already have a reservation for the day
            if (reservation.getEmployee().getRole() == RoleEnum.WORKER) {
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
        Reservation savedReservation = repo.save(reservation);
        return ReservationMapper.INSTANCE.mapToReservationDTO(savedReservation);
    }

    @Override
    public ReservationDTO getReservationById(Long reservationId) {
        Reservation reservation = repo.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));
        return ReservationMapper.INSTANCE.mapToReservationDTO(reservation);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return repo.findAll().stream()
                .map(ReservationMapper.INSTANCE::mapToReservationDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationDTO updateReservation(Long reservationId, ReservationDTO updatedReservationDTO) {
        Reservation existingReservation = repo.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        deleteReservation(reservationId);
        return createReservation(updatedReservationDTO);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        // Fetch the reservation by ID
        Reservation reservation = repo.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + reservationId));

        // Remove the reservation from the desk's unavailable dates
        if (reservation.getDesk() != null && reservation.getDeskDate() != null) {
            reservation.getDesk().getUnavailableDates().remove(reservation.getDeskDate());
        }

        // Delete the reservation
        repo.delete(reservation);
    }

    public List<ReservationDTO> getReservationHistoryForLastMonth(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        List<Reservation> reservations = repo.findByEmployeeAndResDateAfter(employee, oneMonthAgo);

        // Mapping reservations to DTOs
        return reservations.stream()
                .map(ReservationMapper.INSTANCE::mapToReservationDTO)
                .collect(Collectors.toList());
    }

    private boolean checkExistingReservation(ReservationDTO reservationDTO) {
        // Find the employee based on empId in ReservationDTO
        Employee employee = employeeRepository.findById(reservationDTO.getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + reservationDTO.getEmpId()));

        // Get the deskDate from reservationDTO
        LocalDate deskDate = reservationDTO.getDeskDate(); // Assuming deskDate is when the desk will be used

        // Query the repository to find any reservation for the employee on the same deskDate
        List<Reservation> existingReservations = repo.findByEmployeeAndDeskDate(employee, deskDate);

        // Return true if there's already a reservation for the same deskDate
        return !existingReservations.isEmpty();
    }

}
