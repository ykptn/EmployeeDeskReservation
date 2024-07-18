package com.eyl.backend.mapper;

import com.eyl.backend.dto.CompanyDTO;
import com.eyl.backend.entity.Company;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-17T21:26:19+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class CompanyMapperImpl implements CompanyMapper {

    @Override
    public CompanyDTO mapToCompanyDTO(Company company) {
        if ( company == null ) {
            return null;
        }

        CompanyDTO companyDTO = new CompanyDTO();

        companyDTO.setComId( company.getComId() );
        companyDTO.setComName( company.getComName() );

        return companyDTO;
    }

    @Override
    public Company mapToCompany(CompanyDTO companyDTO) {
        if ( companyDTO == null ) {
            return null;
        }

        Company company = new Company();

        company.setComId( companyDTO.getComId() );
        company.setComName( companyDTO.getComName() );

        return company;
    }
}
