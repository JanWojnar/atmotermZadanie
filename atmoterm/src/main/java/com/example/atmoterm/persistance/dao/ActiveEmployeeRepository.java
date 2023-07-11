package com.example.atmoterm.persistance.dao;

import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveEmployeeRepository extends JpaRepository<ActiveEmployeeEntity, Long> {
}
