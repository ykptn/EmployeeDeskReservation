package com.eyl.backend.service.serviceInterfaces;

import com.eyl.backend.dto.EmployeeDTO;

import java.util.List;

public interface IEmployeeService {
    EmployeeDTO createUser(EmployeeDTO userDTO);
    EmployeeDTO getUserById(Long userId);
    List<EmployeeDTO> getAllUsers();
    EmployeeDTO updateUser(Long userId, EmployeeDTO updatedUserDTO);
    void deleteUser(Long userId);


}
