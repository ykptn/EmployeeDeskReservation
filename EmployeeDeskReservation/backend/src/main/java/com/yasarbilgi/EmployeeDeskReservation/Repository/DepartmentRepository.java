package com.yasarbilgi.EmployeeDeskReservation.Repository;

import com.yasarbilgi.EmployeeDeskReservation.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}