package com.eyl.backend.service.serviceInterfaces;

import com.eyl.backend.dto.CompanyDTO;

import java.util.List;

public interface ICompanyService {
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO getCompanyById(Long companyId);
    List<CompanyDTO> getAllCompanies();
    CompanyDTO updateCompany(Long companyId, CompanyDTO updatedCompanyDTO);
    void deleteCompany(Long companyId);
}
