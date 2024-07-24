package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.EmployeeDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "department.depId", target = "depId")
    @Mapping(source = "company.comId", target = "comId")
    EmployeeDTO mapToEmployeeDTO(Employee employee);

    @Mapping(source = "depId", target = "department.depId")
    @Mapping(source = "comId", target = "company.comId")
    Employee mapToEmployee(EmployeeDTO employeeDTO);
}