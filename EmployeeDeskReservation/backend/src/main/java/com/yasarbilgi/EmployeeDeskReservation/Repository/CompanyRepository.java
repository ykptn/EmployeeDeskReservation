package com.yasarbilgi.EmployeeDeskReservation.Repository;

import com.yasarbilgi.EmployeeDeskReservation.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {
}