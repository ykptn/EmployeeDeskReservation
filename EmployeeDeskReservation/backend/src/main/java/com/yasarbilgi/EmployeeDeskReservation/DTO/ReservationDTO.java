package com.yasarbilgi.EmployeeDeskReservation.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long resId;
    private Long empId;
    private Long deskNo;
    private LocalDate deskDate;
}