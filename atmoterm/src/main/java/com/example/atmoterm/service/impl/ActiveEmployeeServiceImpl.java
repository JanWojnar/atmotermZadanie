package com.example.atmoterm.service.impl;

import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.service.mapper.Mapper;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.service.to.EmployeeTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ActiveEmployeeServiceImpl {

    @Autowired
    private ActiveEmployeeRepository activeEmployeeRepository;

    public ActiveEmployeeTo addEmployee(ActiveEmployeeTo employeeTo){
        //validator
        ActiveEmployeeEntity employeeEntity = new ActiveEmployeeEntity();
        if(Objects.nonNull(employeeTo.getId())){
            employeeEntity = activeEmployeeRepository.findById(employeeTo.getId()).orElse(new ActiveEmployeeEntity());
        }
        employeeEntity.setName(employeeTo.getName());
        employeeEntity.setSalary(employeeTo.getSalary());
        employeeEntity.setHireDate(employeeTo.getHireDate());
        //loggowanie jeśli name to null
        return Mapper.map2To(activeEmployeeRepository.save(employeeEntity));
    }

    public List<ActiveEmployeeTo> findAllActiveEmployees(){
        return Mapper.mapActiveEmployees2Tos(activeEmployeeRepository.findAll());
    }
}
