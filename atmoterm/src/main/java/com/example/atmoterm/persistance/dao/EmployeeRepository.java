package com.example.atmoterm.persistance.dao;

import com.example.atmoterm.persistance.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    EmployeeEntity findByName(String name);
}
