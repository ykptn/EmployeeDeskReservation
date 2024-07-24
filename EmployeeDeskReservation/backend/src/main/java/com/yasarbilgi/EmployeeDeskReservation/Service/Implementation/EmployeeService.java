package com.yasarbilgi.EmployeeDeskReservation.Service.Implementation;

import com.yasarbilgi.EmployeeDeskReservation.Message.Message;
import com.yasarbilgi.EmployeeDeskReservation.DTO.EmployeeDTO;
import com.yasarbilgi.EmployeeDeskReservation.DTO.LoginDTO;
import com.yasarbilgi.EmployeeDeskReservation.DTO.PasswordDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Employee;
import com.yasarbilgi.EmployeeDeskReservation.Exception.ResourceNotFoundException;
import com.yasarbilgi.EmployeeDeskReservation.Mapper.EmployeeMapper;
import com.yasarbilgi.EmployeeDeskReservation.Repository.CompanyRepository;
import com.yasarbilgi.EmployeeDeskReservation.Repository.DepartmentRepository;
import com.yasarbilgi.EmployeeDeskReservation.Repository.EmployeeRepository;
import com.yasarbilgi.EmployeeDeskReservation.Service.Interface.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeDTO createUser(EmployeeDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("EmployeeDTO cannot be null");
        }

        Employee employee = EmployeeMapper.INSTANCE.mapToEmployee(userDTO);

        if (employee == null) {
            throw new IllegalArgumentException("Mapping to Employee failed");
        }

        if (userDTO.getDepId() != null) {
            employee.setDepartment(departmentRepository.findById(userDTO.getDepId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        } else {
            throw new IllegalArgumentException("Department ID cannot be null");
        }

        if (userDTO.getComId() != null) {
            employee.setCompany(companyRepository.findById(userDTO.getComId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found")));
        } else {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        String role = userDTO.getRole() != null ? userDTO.getRole() : RoleConstants.DEFAULT_ROLE;
        if (isValidRole(role)) {
            employee.setRole(role);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        String initialPassword = employee.getFirstName() + employee.getLastName();
        String encryptedPassword = passwordEncoder.encode(initialPassword);
        employee.setPassword(encryptedPassword);

        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(savedEmployee);
    }

    private boolean isValidRole(String role) {
        List<String> validRoles = Arrays.asList(RoleConstants.MANAGER, RoleConstants.USER); // Define valid roles here
        return validRoles.contains(role);
    }
    @Override
    public EmployeeDTO getUserById(Long userId) {
        Employee user = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(user);
    }

    @Override
    public List<EmployeeDTO> getAllUsers() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateUser(Long userId, EmployeeDTO updatedUserDTO) {
        Employee existingEmployee = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        if (updatedUserDTO.getFirstName() != null) {
            existingEmployee.setFirstName(updatedUserDTO.getFirstName());
        }
        if (updatedUserDTO.getLastName() != null) {
            existingEmployee.setLastName(updatedUserDTO.getLastName());
        }
        if (updatedUserDTO.getEmail() != null) {
            existingEmployee.setEmail(updatedUserDTO.getEmail());
        }
        if (updatedUserDTO.getDepId() != null) {
            existingEmployee.setDepartment(departmentRepository.findById(updatedUserDTO.getDepId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        }
        if (updatedUserDTO.getComId() != null) {
            existingEmployee.setCompany(companyRepository.findById(updatedUserDTO.getComId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found")));
        }
        if (updatedUserDTO.getRole() != null) {
            existingEmployee.setRole(updatedUserDTO.getRole());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(updatedEmployee);
    }

    @Override
    public void deleteUser(Long userId) {
        Employee user = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        employeeRepository.delete(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee user = employeeRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
    @Override
    public Message login(LoginDTO loginDTO) {
        Employee user = employeeRepository.findByEmail(loginDTO.getEmail());
        if (user != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = user.getPassword();
            if (passwordEncoder.matches(password, encodedPassword)) {
                return new Message("Login Success", true);
            } else {
                return new Message("Login Failed: Incorrect password", false);
            }
        } else {
            return new Message("Email not found", false);
        }
    }
    @Override
    public Message changePassword(Long userId, PasswordDTO passwordDTO) {
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + userId));

        String encryptedPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
        employee.setPassword(encryptedPassword);
        employeeRepository.save(employee);

        return new Message("Şifreniz başarıyla değiştirildi", true);
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(employee);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void updatePassword(Long userId, String encryptedPassword) {
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + userId));
        employee.setPassword(encryptedPassword);
        employeeRepository.save(employee);

    }

    @Override
    public List<Employee> searchEmployees(String query) {
        List<Employee> employees = employeeRepository.searchEmployees(query);
        return employees;
    }
}