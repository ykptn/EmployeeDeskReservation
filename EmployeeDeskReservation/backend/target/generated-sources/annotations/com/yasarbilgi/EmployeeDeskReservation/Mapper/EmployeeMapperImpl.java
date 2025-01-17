package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.EmployeeDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Company;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Department;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Employee;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T14:46:10+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
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
        employeeDTO.setEmpId( employee.getEmpId() );
        employeeDTO.setFirstName( employee.getFirstName() );
        employeeDTO.setLastName( employee.getLastName() );
        employeeDTO.setEmail( employee.getEmail() );
        employeeDTO.setRole( employee.getRole() );

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
        employee.setEmpId( employeeDTO.getEmpId() );
        employee.setFirstName( employeeDTO.getFirstName() );
        employee.setLastName( employeeDTO.getLastName() );
        employee.setEmail( employeeDTO.getEmail() );
        employee.setRole( employeeDTO.getRole() );

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
