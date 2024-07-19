package com.eyl.backend.mapper;

import com.eyl.backend.dto.DepartmentDTO;
import com.eyl.backend.entity.Department;
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
