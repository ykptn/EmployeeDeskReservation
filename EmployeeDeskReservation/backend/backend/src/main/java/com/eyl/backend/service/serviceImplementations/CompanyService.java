package com.eyl.backend.service.serviceImplementations;

import com.eyl.backend.dto.CompanyDTO;
import com.eyl.backend.entity.Company;
import com.eyl.backend.exception.ResourceNotFoundException;
import com.eyl.backend.mapper.CompanyMapper;
import com.eyl.backend.repository.CompanyRepository;
import com.eyl.backend.service.serviceInterfaces.ICompanyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository repo;


    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = CompanyMapper.INSTANCE.mapToCompany(companyDTO);
        Company savedCompany = repo.save(company);
        return CompanyMapper.INSTANCE.mapToCompanyDTO(savedCompany);
    }

    @Override
    public CompanyDTO getCompanyById(Long companyId) {
        Company company = repo.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
        return CompanyMapper.INSTANCE.mapToCompanyDTO(company);
    }

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return repo.findAll().stream()
                .map(CompanyMapper.INSTANCE::mapToCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDTO updateCompany(Long companyId, CompanyDTO updatedCompanyDTO) {
        Company company = repo.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
        company.setComName(updatedCompanyDTO.getComName());
        Company updatedCompany = repo.save(company);
        return CompanyMapper.INSTANCE.mapToCompanyDTO(updatedCompany);
    }

    @Override
    public void deleteCompany(Long companyId) {
        Company company = repo.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
        repo.delete(company);
    }
}
