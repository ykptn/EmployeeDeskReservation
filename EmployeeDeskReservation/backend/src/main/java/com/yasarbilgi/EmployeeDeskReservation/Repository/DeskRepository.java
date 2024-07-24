package com.yasarbilgi.EmployeeDeskReservation.Repository;

import com.yasarbilgi.EmployeeDeskReservation.Entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk,Long> {
}