package com.eyl.backend.service.serviceInterfaces;

import com.eyl.backend.LoginMessage;
import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.dto.LoginDTO;
import com.eyl.backend.dto.PasswordDTO;
import com.eyl.backend.entity.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IEmployeeService {
    EmployeeDTO createUser(EmployeeDTO userDTO);
    EmployeeDTO getUserById(Long userId);
    List<EmployeeDTO> getAllUsers();
    EmployeeDTO updateUser(Long userId, EmployeeDTO updatedUserDTO);
    void deleteUser(Long userId);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    LoginMessage login(LoginDTO loginDTO);


    void changePassword(Long userId, PasswordDTO passwordDTO);

    EmployeeDTO findByEmail(String email);

    String encodePassword(String password);

    void updatePassword(Long userId, String encryptedPassword);

    List<Employee> searchEmployees(String query);
}
