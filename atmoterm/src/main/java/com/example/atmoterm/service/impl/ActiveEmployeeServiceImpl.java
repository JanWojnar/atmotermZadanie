package com.example.atmoterm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.atmoterm.common.mapper.Mapper;
import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;
import com.example.atmoterm.service.EmployeeService;
import com.example.atmoterm.service.to.ActiveEmployeeTo;

@Service
public class ActiveEmployeeServiceImpl implements EmployeeService<ActiveEmployeeTo> {

    @Autowired
    private ActiveEmployeeRepository activeEmployeeRepository;

    @Autowired
    private TeamRepository teamRepository;

    public ActiveEmployeeTo addEmployee(ActiveEmployeeTo employeeTo) {
        ActiveEmployeeEntity employeeEntity =
            this.activeEmployeeRepository.findById(employeeTo.getId()).orElse(new ActiveEmployeeEntity());
        employeeEntity.setName(employeeTo.getName());
        employeeEntity.setSalary(employeeTo.getSalary());
        employeeEntity.setHireDate(employeeTo.getHireDate());
        return Mapper.map2To(this.activeEmployeeRepository.save(employeeEntity));
    }

    public ActiveEmployeeTo updateEmployeeById(ActiveEmployeeTo activeEmployeeTo) {
        ActiveEmployeeEntity foundEmployee =
            this.activeEmployeeRepository.findById(activeEmployeeTo.getId()).orElse(null);
        ActiveEmployeeTo outcome = new ActiveEmployeeTo();
        if (Objects.nonNull(foundEmployee)) {
            if (Objects.nonNull(activeEmployeeTo.getName())) {
                foundEmployee.setName(activeEmployeeTo.getName());
            }
            if (Objects.nonNull(activeEmployeeTo.getHireDate())) {
                foundEmployee.setHireDate(activeEmployeeTo.getHireDate());
            }
            if (Objects.nonNull(activeEmployeeTo.getSalary())) {
                foundEmployee.setSalary(activeEmployeeTo.getSalary());
            }
            outcome = Mapper.map2To(this.activeEmployeeRepository.save(foundEmployee));
        } else {
            outcome.getErrorList().add("Employee with given ID does not exist!");
        }
        return outcome;
    }

    public ActiveEmployeeTo removeEmployeeWithName(ActiveEmployeeTo employeeTo) {

        ActiveEmployeeEntity foundEmployee = this.activeEmployeeRepository.findByName(employeeTo.getName());
        ActiveEmployeeTo outcome = new ActiveEmployeeTo();
        if (Objects.nonNull(foundEmployee)) {
            outcome = Mapper.map2To(foundEmployee);
            List<TeamEntity> teamsToClearFromEmployee = new ArrayList<>(foundEmployee.getTeams());
            for (TeamEntity team : teamsToClearFromEmployee) {
                team.removeEmployee(foundEmployee);
                this.teamRepository.save(team);
            }
            this.activeEmployeeRepository.delete(foundEmployee);
        } else {
            outcome.getErrorList().add("Employee with given name does not exist!");
        }
        return outcome;
    }

    public ActiveEmployeeTo findByName(ActiveEmployeeTo employeeTo) {
        return Mapper.map2To(this.activeEmployeeRepository.findByName(employeeTo.getName()));
    }

    public List<ActiveEmployeeTo> findAllEmployees() {
        return Mapper.mapActiveEmployees2Tos(this.activeEmployeeRepository.findAll());
    }
}
