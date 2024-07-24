package com.eyl.backend.mapper;


import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
