package com.yasarbilgi.EmployeeDeskReservation.Service.Interface;

import com.yasarbilgi.EmployeeDeskReservation.DTO.CompanyDTO;

import java.util.List;

public interface ICompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO getCompanyById(Long companyId);
    List<CompanyDTO> getAllCompanies();
    CompanyDTO updateCompany(Long companyId, CompanyDTO updatedCompanyDTO);
    void deleteCompany(Long companyId);
}