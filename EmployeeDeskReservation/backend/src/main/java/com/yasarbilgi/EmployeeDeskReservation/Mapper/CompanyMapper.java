package com.yasarbilgi.EmployeeDeskReservation.Mapper;

import com.yasarbilgi.EmployeeDeskReservation.DTO.CompanyDTO;
import com.yasarbilgi.EmployeeDeskReservation.Entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO mapToCompanyDTO(Company company);

    Company mapToCompany(CompanyDTO companyDTO);
}