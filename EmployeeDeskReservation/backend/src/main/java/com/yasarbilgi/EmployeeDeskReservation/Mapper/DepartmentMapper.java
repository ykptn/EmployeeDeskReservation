package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DepartmentDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper {

    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    @Mapping(source = "company.comId", target = "comId")
    DepartmentDTO mapToDepartmentDTO(Department department);

    @Mapping(source = "comId", target = "company.comId")
    Department mapToDepartment(DepartmentDTO departmentDTO);
}