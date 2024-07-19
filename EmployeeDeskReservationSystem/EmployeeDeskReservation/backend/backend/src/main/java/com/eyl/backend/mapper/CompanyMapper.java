package com.eyl.backend.mapper;

import com.eyl.backend.dto.CompanyDTO;
import com.eyl.backend.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO mapToCompanyDTO(Company company);

    Company mapToCompany(CompanyDTO companyDTO);
}
