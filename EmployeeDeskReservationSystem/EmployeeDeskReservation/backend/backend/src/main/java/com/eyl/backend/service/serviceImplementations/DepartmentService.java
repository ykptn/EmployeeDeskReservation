package com.eyl.backend.service.serviceImplementations;

import com.eyl.backend.dto.DepartmentDTO;
import com.eyl.backend.entity.Department;
import com.eyl.backend.exception.ResourceNotFoundException;
import com.eyl.backend.mapper.DepartmentMapper;
import com.eyl.backend.repository.CompanyRepository;
import com.eyl.backend.repository.DepartmentRepository;
import com.eyl.backend.service.serviceInterfaces.IDepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final DepartmentRepository repo;
    private final CompanyRepository companyRepository;


    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = DepartmentMapper.INSTANCE.mapToDepartment(departmentDTO);

        // Validate and set Company
        if (departmentDTO.getComId() != null) {
            department.setCompany(companyRepository.findById(departmentDTO.getComId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found")));
        } else {
            throw new IllegalArgumentException("Company ID cannot be null");
        }

        Department savedDepartment = repo.save(department);
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(savedDepartment);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long departmentId) {
        Department department = repo.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return repo.findAll().stream()
                .map(DepartmentMapper.INSTANCE::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(Long departmentId, DepartmentDTO updatedDepartmentDTO) {
        Department existingDepartment = repo.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id:"+departmentId));

        if (updatedDepartmentDTO.getDepName() != null) {
            existingDepartment.setDepName(updatedDepartmentDTO.getDepName());
        }
        if (updatedDepartmentDTO.getComId() != null) {
            existingDepartment.setCompany(companyRepository.findById(updatedDepartmentDTO.getComId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company not found")));
        }

        Department updatedDepartment = repo.save(existingDepartment);
        return DepartmentMapper.INSTANCE.mapToDepartmentDTO(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = repo.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        repo.delete(department);
    }
}
