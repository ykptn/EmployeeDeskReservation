package com.yasarbilgi.EmployeeDeskReservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    private Long depId;
    private Long comId;
    private String role;
}