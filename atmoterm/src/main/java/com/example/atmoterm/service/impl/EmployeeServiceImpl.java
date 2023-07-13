package com.example.atmoterm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.atmoterm.common.mapper.Mapper;
import com.example.atmoterm.persistance.dao.EmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;
import com.example.atmoterm.service.EmployeeService;
import com.example.atmoterm.service.to.EmployeeTo;

@Service
public class EmployeeServiceImpl implements EmployeeService<EmployeeTo> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ActiveEmployeeServiceImpl activeEmployeeService;

    @Autowired
    private TeamRepository teamRepository;

    public EmployeeTo addEmployee(EmployeeTo employeeTo){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employeeTo.getName());
        return Mapper.map2To(this.employeeRepository.save(employeeEntity));
    }

    public EmployeeTo updateEmployeeById(EmployeeTo employeeTo) {
        EmployeeEntity foundEmployee = this.employeeRepository.findById(employeeTo.getId()).orElse(null);
        EmployeeTo outcome = new EmployeeTo();
        if(Objects.nonNull(foundEmployee)){
            foundEmployee.setName(employeeTo.getName());
            outcome = Mapper.map2To(this.employeeRepository.save(foundEmployee));
        } else {
            outcome.getErrorList().add("Employee with given ID does not exist!");
        }
        return outcome;
    }


    public EmployeeTo removeEmployeeWithName(EmployeeTo employeeTo){

        EmployeeEntity foundEmployee = this.employeeRepository.findByName(employeeTo.getName());
        EmployeeTo outcome = new EmployeeTo();
        if(Objects.nonNull(foundEmployee)){
            outcome = Mapper.map2To(foundEmployee);
            List<TeamEntity> teamsToClearFromEmployee = new ArrayList<>(foundEmployee.getTeams());
            for(TeamEntity team : teamsToClearFromEmployee){
                team.removeEmployee(foundEmployee);
                this.teamRepository.save(team);
            }
            this.employeeRepository.delete(foundEmployee);
        } else {
            outcome.setErrorList(List.of("Employee with given name does not exist!"));
        }
        return outcome;
    }

    public EmployeeTo findByName(EmployeeTo employeeTo){
        return Mapper.map2To(this.employeeRepository.findByName(employeeTo.getName()));
    }

    public List<EmployeeTo> findAllEmployees(){
        return Mapper.mapEmployees2Tos(this.employeeRepository.findAll());
    }

    public List<EmployeeTo> findInactiveEmployees(){
        List<EmployeeTo> allEmployees = this.findAllEmployees();
        List<Long> idsOfActiveEmployees = this.activeEmployeeService.findAllEmployees()
                .stream().map(EmployeeTo::getId).collect(Collectors.toList());

        return allEmployees
                .stream().filter(employee -> !idsOfActiveEmployees.contains(employee.getId())).collect(Collectors.toList());
    }
}
