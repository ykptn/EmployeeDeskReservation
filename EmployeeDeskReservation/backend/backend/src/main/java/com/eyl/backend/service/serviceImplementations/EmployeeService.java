package com.eyl.backend.service.serviceImplementations;

import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.dto.ReservationDTO;
import com.eyl.backend.entity.Employee;
import com.eyl.backend.exception.ResourceNotFoundException;
import com.eyl.backend.mapper.EmployeeMapper;
import com.eyl.backend.repository.CompanyRepository;
import com.eyl.backend.repository.DepartmentRepository;
import com.eyl.backend.repository.EmployeeRepository;
import com.eyl.backend.service.serviceInterfaces.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository repo;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    @Override
    public EmployeeDTO createUser(EmployeeDTO userDTO) {
        Employee employee = EmployeeMapper.INSTANCE.mapToEmployee(userDTO);

        // Validate and set Department
        if (userDTO.getDepId() != null) {
            employee.setDepartment(departmentRepository.findById(userDTO.getDepId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        } else {
            throw new IllegalArgumentException("Department ID cannot be null");
        }

        // Validate and set Company
        if (userDTO.getComId() != null) {
            employee.setCompany(companyRepository.findById(userDTO.getComId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found")));
        } else {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        employee.setPassword(employee.getFirstName()+employee.getLastName());

        Employee savedEmployee = repo.save(employee);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getUserById(Long userId) {
        Employee user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(user);
    }

    @Override
    public List<EmployeeDTO> getAllUsers() {
        return repo.findAll().stream()
                .map(EmployeeMapper.INSTANCE::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateUser(Long userId, EmployeeDTO updatedUserDTO) {
        Employee existingEmployee = repo.findById(userId)
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
        if (updatedUserDTO.getUserRole() != null) {
            existingEmployee.setRole(EmployeeMapper.INSTANCE.stringToRole(updatedUserDTO.getUserRole()));
        }

        Employee updatedEmployee = repo.save(existingEmployee);
        return EmployeeMapper.INSTANCE.mapToEmployeeDTO(updatedEmployee);
    }

    @Override
    public void deleteUser(Long userId) {
        Employee user = repo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        repo.delete(user);
    }



}
