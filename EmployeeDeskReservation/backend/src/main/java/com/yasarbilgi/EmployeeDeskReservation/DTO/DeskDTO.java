package com.yasarbilgi.EmployeeDeskReservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DeskDTO {
    private Long deskNo;
    private String room;
    private Set<LocalDate> unavailableDates = new HashSet<>();
}