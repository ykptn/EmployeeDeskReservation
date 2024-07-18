package com.eyl.backend.dto;


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
    private String userRole;

}
