package com.eyl.backend.service.serviceInterfaces;

import com.eyl.backend.dto.DepartmentDTO;

import java.util.List;

public interface IDepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentById(Long departmentId);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO updatedDepartmentDTO);
    void deleteDepartment(Long departmentId);
}
