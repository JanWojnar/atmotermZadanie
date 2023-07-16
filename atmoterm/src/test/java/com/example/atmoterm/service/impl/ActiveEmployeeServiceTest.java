package com.example.atmoterm.service.impl;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.dao.EmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.testing.ExampleDatabaseInitializer;

@SpringBootTest
@ActiveProfiles(profiles = { "exampleDatabase" })
class ActiveEmployeeServiceTest {

    @Autowired
    ActiveEmployeeServiceImpl activeEmployeeService;

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ActiveEmployeeRepository activeEmployeeRepository;

    @Autowired
    ExampleDatabaseInitializer databaseInitializer;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        this.teamRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.activeEmployeeRepository.deleteAll();

        this.databaseInitializer.init();
    }

    @AfterEach
    void tearDown() {
        this.teamRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.activeEmployeeRepository.deleteAll();
    }

    @Test
    void addEmployeeShouldAddNewEmployee() {

        // GIVEN
        ActiveEmployeeTo activeEmployeeTo = ActiveEmployeeTo.builder()
            .name("XXXXX")
            .hireDate(LocalDate.now())
            .salary(3500.0)
            .build();
        // WHEN
        this.activeEmployeeService.addEmployee(activeEmployeeTo);
        // THEN
        ActiveEmployeeTo foundNewEmployee = this.activeEmployeeService.findByName(activeEmployeeTo);

        Assertions.assertAll(
            () -> Assertions.assertEquals(activeEmployeeTo.getName(), foundNewEmployee.getName()),
            () -> Assertions.assertEquals(activeEmployeeTo.getSalary(), foundNewEmployee.getSalary()),
            () -> Assertions.assertEquals(activeEmployeeTo.getHireDate(), foundNewEmployee.getHireDate()));
    }

    @Test
    void updateEmployeeByName() {
        // GIVEN
        ActiveEmployeeTo employeeToUpdate =
            this.activeEmployeeService.findByName(ActiveEmployeeTo.builder().name("Blazej").build());

        ActiveEmployeeTo newEmpData = ActiveEmployeeTo.builder()
            .id(employeeToUpdate.getId())
            .name("XXXXX")
            .hireDate(LocalDate.now())
            .salary(3500.0)
            .build();
        // WHEN
        this.activeEmployeeService.updateEmployeeById(newEmpData);
        // THEN
        ActiveEmployeeTo updatedEmployee = this.activeEmployeeService
            .findByName(ActiveEmployeeTo.builder().name(newEmpData.getName()).build());

        Assertions.assertAll(
            () -> Assertions.assertEquals(employeeToUpdate.getId(), updatedEmployee.getId()),
            () -> Assertions.assertEquals(employeeToUpdate.getVersionNumber() + 1,
                updatedEmployee.getVersionNumber()),
            () -> Assertions.assertEquals(newEmpData.getName(), updatedEmployee.getName()),
            () -> Assertions.assertEquals(newEmpData.getSalary(), updatedEmployee.getSalary()),
            () -> Assertions.assertEquals(newEmpData.getHireDate(), updatedEmployee.getHireDate()));
    }

}