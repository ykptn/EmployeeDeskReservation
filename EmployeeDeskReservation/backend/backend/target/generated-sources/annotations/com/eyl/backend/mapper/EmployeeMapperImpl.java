package com.eyl.backend.mapper;

import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.entity.Company;
import com.eyl.backend.entity.Department;
import com.eyl.backend.entity.Employee;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T21:26:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO mapToEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setDepId( employeeDepartmentDepId( employee ) );
        employeeDTO.setComId( employeeCompanyComId( employee ) );
        employeeDTO.setUserRole( roleToString( employee.getRole() ) );
        employeeDTO.setEmpId( employee.getEmpId() );
        employeeDTO.setFirstName( employee.getFirstName() );
        employeeDTO.setLastName( employee.getLastName() );
        employeeDTO.setEmail( employee.getEmail() );

        return employeeDTO;
    }

    @Override
    public Employee mapToEmployee(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setDepartment( employeeDTOToDepartment( employeeDTO ) );
        employee.setCompany( employeeDTOToCompany( employeeDTO ) );
        employee.setRole( stringToRole( employeeDTO.getUserRole() ) );
        employee.setEmpId( employeeDTO.getEmpId() );
        employee.setFirstName( employeeDTO.getFirstName() );
        employee.setLastName( employeeDTO.getLastName() );
        employee.setEmail( employeeDTO.getEmail() );

        return employee;
    }

    private Long employeeDepartmentDepId(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Department department = employee.getDepartment();
        if ( department == null ) {
            return null;
        }
        Long depId = department.getDepId();
        if ( depId == null ) {
            return null;
        }
        return depId;
    }

    private Long employeeCompanyComId(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        Company company = employee.getCompany();
        if ( company == null ) {
            return null;
        }
        Long comId = company.getComId();
        if ( comId == null ) {
            return null;
        }
        return comId;
    }

    protected Department employeeDTOToDepartment(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Department department = new Department();

        department.setDepId( employeeDTO.getDepId() );

        return department;
    }

    protected Company employeeDTOToCompany(EmployeeDTO employeeDTO) {
        if ( employeeDTO == null ) {
            return null;
        }

        Company company = new Company();

        company.setComId( employeeDTO.getComId() );

        return company;
    }
}
