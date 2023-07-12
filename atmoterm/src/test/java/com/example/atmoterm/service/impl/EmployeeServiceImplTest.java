package com.example.atmoterm.service.impl;

import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.dao.DataGenerator;
import com.example.atmoterm.persistance.dao.EmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;
import com.example.atmoterm.service.mapper.Mapper;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.service.to.EmployeeTo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    EmployeeServiceImpl employeeService;

    @Autowired
    ActiveEmployeeServiceImpl activeEmployeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ActiveEmployeeRepository activeEmployeeRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
        employeeRepository.deleteAll();
        activeEmployeeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        teamRepository.deleteAll();
        employeeRepository.deleteAll();
        activeEmployeeRepository.deleteAll();
    }

    @Test
    void shouldSaveNewEmployeeAndFindItUsingService(){
        EmployeeTo employee = EmployeeTo.builder().name("Jan").build();
        employeeService.addEmployee(employee);
        EmployeeTo foundEmployee = employeeService.findByName(employee);
        Assertions.assertNotNull(foundEmployee);
        Assertions.assertNotNull(foundEmployee.getId());
        Assertions.assertEquals(employee.getName(), foundEmployee.getName());
    }

    @Test
    void shouldFindOnlyInactiveEmployees(){
        EmployeeTo employee1 = EmployeeTo.builder().name("Jan").build();
        EmployeeTo employee2 = EmployeeTo.builder().name("Zuzanna").build();
        EmployeeTo employee3 = EmployeeTo.builder().name("Klara").build();

        employee1 = employeeService.addEmployee(employee1);
        employee2 = employeeService.addEmployee(employee2);
        employee3 = employeeService.addEmployee(employee3);

        ActiveEmployeeTo activeEmployee1 = ActiveEmployeeTo.builder()
                .name("Kalmar")
                .hireDate(LocalDate.of(2000,2,2))
                .salary(5000.0)
                .build();

        ActiveEmployeeTo activeEmployee2 = ActiveEmployeeTo.builder()
                .name("Ostryga")
                .hireDate(LocalDate.of(2000,2,3))
                .salary(5000.0)
                .build();

        activeEmployeeService.addEmployee(activeEmployee1);
        activeEmployeeService.addEmployee(activeEmployee2);

        List<EmployeeTo> foundGenericEmployees = employeeService.findAllEmployees();
        List<ActiveEmployeeTo> foundActiveEmployees = activeEmployeeService.findAllActiveEmployees();

        Assertions.assertEquals(5,foundGenericEmployees.size());
        Assertions.assertEquals(2, foundActiveEmployees.size());

        List<EmployeeTo> foundInactiveEmployees = employeeService.findInactiveEmployees();
        Assertions.assertEquals(3, foundInactiveEmployees.size());
        Assertions.assertTrue(foundInactiveEmployees.containsAll(List.of(employee1,employee2,employee3)));
    }

    @Test
    void shouldRemovedEmployeeThatIsAssignedToTeamsBeAbsentInDatabaseAndAlsoAbsentInThoseTeams(){
        //given
        EmployeeEntity employee1 = EmployeeEntity.builder().name("Jan").build();
        EmployeeEntity employee2 = EmployeeEntity.builder().name("Zuzanna").build();
        EmployeeEntity employee3 = EmployeeEntity.builder().name("Karol").build();
        EmployeeEntity employee4 = EmployeeEntity.builder().name("Kasia").build();
        EmployeeEntity employee5 = EmployeeEntity.builder().name("Marek").build();

        TeamEntity teamGirlsEntity = TeamEntity.builder().name("TEAM_GIRLS").build();
        TeamEntity teamBoysEntity = TeamEntity.builder().name("TEAM_BOYS").build();
        TeamEntity teamAllEntity = TeamEntity.builder().name("TEAM_ALL").build();

        DataGenerator.save5EmployeesIn3Teams(employee1, employee2, employee3, employee4, employee5,
                teamGirlsEntity, teamBoysEntity, teamAllEntity,
                employeeRepository, teamRepository);

        //when
        EmployeeTo removedEmployee = employeeService.removeEmployeeWithName(EmployeeTo.builder().name("Karol").build());

        //then
        Assertions.assertNull(employeeRepository.findByName("Karol"));

        teamBoysEntity = teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = teamRepository.findByName("TEAM_ALL");
        Assertions.assertFalse(teamAllEntity.getEmployees().contains(Mapper.map2Entity(removedEmployee)));
        Assertions.assertFalse(teamBoysEntity.getEmployees().contains(Mapper.map2Entity(removedEmployee)));
        Assertions.assertTrue(teamBoysEntity.getEmployees().contains(employeeRepository.findByName("Jan")));

    }

}