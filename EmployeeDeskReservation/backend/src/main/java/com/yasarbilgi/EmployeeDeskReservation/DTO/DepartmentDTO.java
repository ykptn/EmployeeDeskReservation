package com.yasarbilgi.EmployeeDeskReservation.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Long depId;
    private String depName;
    private Long comId;
}