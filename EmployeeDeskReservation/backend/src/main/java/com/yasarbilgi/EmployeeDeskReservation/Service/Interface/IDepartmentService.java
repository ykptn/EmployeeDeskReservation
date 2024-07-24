package com.yasarbilgi.EmployeeDeskReservation.Service.Interface;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DepartmentDTO;
import com.yasarbilgi.EmployeeDeskReservation.Message.Message;

import java.util.List;

public interface IDepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartmentById(Long departmentId);
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO updatedDepartmentDTO);
    Message deleteDepartment(Long departmentId);
}