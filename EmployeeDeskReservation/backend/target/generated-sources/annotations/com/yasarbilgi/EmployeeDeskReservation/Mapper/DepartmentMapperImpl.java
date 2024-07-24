package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.DepartmentDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Company;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Department;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-24T14:46:10+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class DepartmentMapperImpl implements DepartmentMapper {

    @Override
    public DepartmentDTO mapToDepartmentDTO(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentDTO departmentDTO = new DepartmentDTO();

        departmentDTO.setComId( departmentCompanyComId( department ) );
        departmentDTO.setDepId( department.getDepId() );
        departmentDTO.setDepName( department.getDepName() );

        return departmentDTO;
    }

    @Override
    public Department mapToDepartment(DepartmentDTO departmentDTO) {
        if ( departmentDTO == null ) {
            return null;
        }

        Department department = new Department();

        department.setCompany( departmentDTOToCompany( departmentDTO ) );
        department.setDepId( departmentDTO.getDepId() );
        department.setDepName( departmentDTO.getDepName() );

        return department;
    }

    private Long departmentCompanyComId(Department department) {
        if ( department == null ) {
            return null;
        }
        Company company = department.getCompany();
        if ( company == null ) {
            return null;
        }
        Long comId = company.getComId();
        if ( comId == null ) {
            return null;
        }
        return comId;
    }

    protected Company departmentDTOToCompany(DepartmentDTO departmentDTO) {
        if ( departmentDTO == null ) {
            return null;
        }

        Company company = new Company();

        company.setComId( departmentDTO.getComId() );

        return company;
    }
}
