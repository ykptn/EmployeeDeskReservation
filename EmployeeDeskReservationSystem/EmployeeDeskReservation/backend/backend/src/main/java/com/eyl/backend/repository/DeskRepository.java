package com.eyl.backend.repository;

import com.eyl.backend.entity.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeskRepository extends JpaRepository<Desk,Long> {
}
