package com.example.atmoterm.persistance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;

public interface ActiveEmployeeRepository extends JpaRepository<ActiveEmployeeEntity, Long> {
    ActiveEmployeeEntity findByName(String name);
}
