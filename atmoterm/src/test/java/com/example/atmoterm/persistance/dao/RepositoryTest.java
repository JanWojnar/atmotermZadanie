package com.example.atmoterm.persistance.dao;

import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
class RepositoryTest {

    @Autowired
    ActiveEmployeeRepository activeEmployeeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    @Validated
    void repositoryTest(){

        //CREATING AND SAVING EMPLOYEES WITHOUT TEAM

        EmployeeEntity employee1 = EmployeeEntity.builder().name("Jan").build();
        EmployeeEntity employee2 = EmployeeEntity.builder().name("Zuzanna").build();
        EmployeeEntity employee3 = EmployeeEntity.builder().name("Karol").build();
        EmployeeEntity employee4 = EmployeeEntity.builder().name("Kasia").build();
        EmployeeEntity employee5 = EmployeeEntity.builder().name("Marek").build();
        employeeRepository.saveAll(List.of(employee1,employee2,employee3,employee4,employee5));

        Assertions.assertEquals(employee1.getName(), employeeRepository.findByName("Jan").getName());
        Assertions.assertEquals(employee2.getName(), employeeRepository.findByName("Zuzanna").getName());
        Assertions.assertEquals(employee3.getName(), employeeRepository.findByName("Karol").getName());
        Assertions.assertEquals(employee4.getName(), employeeRepository.findByName("Kasia").getName());
        Assertions.assertEquals(employee5.getName(), employeeRepository.findByName("Marek").getName());

        employee1 = employeeRepository.findByName("Jan");
        employee2 = employeeRepository.findByName("Zuzanna");
        employee3 = employeeRepository.findByName("Karol");
        employee4 = employeeRepository.findByName("Kasia");
        employee5 = employeeRepository.findByName("Marek");

        //CREATING AND SAVING TEAMS WITHOUT EMPLOYEES

        TeamEntity teamGirlsEntity = TeamEntity.builder().name("TEAM_GIRLS").build();
        TeamEntity teamBoysEntity = TeamEntity.builder().name("TEAM_BOYS").build();
        TeamEntity teamAllEntity = TeamEntity.builder().name("TEAM_ALL").build();

        teamRepository.saveAll(List.of(teamGirlsEntity,teamBoysEntity,teamAllEntity));

        //ADDING TEAM TO EMPLOYEES
        employee2.addTeam(teamGirlsEntity);
        employee4.addTeam(teamGirlsEntity);

        employee1.addTeam(teamBoysEntity);
        employee3.addTeam(teamBoysEntity);
        employee5.addTeam(teamBoysEntity);

        employee1.addTeam(teamAllEntity);
        employee2.addTeam(teamAllEntity);
        employee3.addTeam(teamAllEntity);
        employee4.addTeam(teamAllEntity);
        employee5.addTeam(teamAllEntity);

        //SAVING TEAMS WITH EMPLOYEES
        teamRepository.saveAll(List.of(teamGirlsEntity,teamBoysEntity,teamAllEntity));

        employee1 = employeeRepository.findByName("Jan");
        employee2 = employeeRepository.findByName("Zuzanna");
        employee3 = employeeRepository.findByName("Karol");
        employee4 = employeeRepository.findByName("Kasia");
        employee5 = employeeRepository.findByName("Marek");

        teamGirlsEntity = teamRepository.findByName("TEAM_GIRLS");
        teamBoysEntity = teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = teamRepository.findByName("TEAM_ALL");

        Assertions.assertTrue(teamGirlsEntity.getEmployees().containsAll(List.of(employee2,employee4)));
        Assertions.assertTrue(teamBoysEntity.getEmployees().containsAll(List.of(employee1,employee3, employee5)));
        Assertions.assertTrue(teamAllEntity.getEmployees().containsAll(List.of(employee1, employee2, employee3, employee4, employee5)));

        //REMOVING TEAM
        teamRepository.delete(teamGirlsEntity);
        Assertions.assertNull(teamRepository.findByName("TEAM_GIRLS"));

        //...DOES NOT REMOVE EMPLOYEES
        employee2 = employeeRepository.findByName("Zuzanna");
        employee4 = employeeRepository.findByName("Kasia");
        Assertions.assertNotNull(employee2);
        Assertions.assertNotNull(employee4);
        Assertions.assertFalse(employee2.getTeams().contains(teamGirlsEntity));
        Assertions.assertFalse(employee4.getTeams().contains(teamGirlsEntity));

        //TO REMOVE EMPLOYEE FIRSTLY DELETE HIM/HER FROM TEAMS!
        employee1 = employeeRepository.findByName("Jan");
        List<TeamEntity> teamsToClearFromEmployee = new ArrayList<>(employee1.getTeams());
        for(TeamEntity team : teamsToClearFromEmployee){
            team.removeEmployee(employee1);
            teamRepository.save(team);
        }
        employeeRepository.delete(employee1);

        teamBoysEntity = teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = teamRepository.findByName("TEAM_ALL");
        Assertions.assertFalse(teamAllEntity.getEmployees().contains(employee1));
        Assertions.assertFalse(teamBoysEntity.getEmployees().contains(employee1));
        Assertions.assertNull(employeeRepository.findByName("Jan"));

        //ADDING ACTIVEEMPLOYEE
        ActiveEmployeeEntity activeEmployeeEntity = ActiveEmployeeEntity.builder().name("Bob").salary(9999.0).hireDate(LocalDate.of(2000,11,12)).build();
        activeEmployeeEntity = activeEmployeeRepository.save(activeEmployeeEntity);

        //ADDING ACTIVEEMPLOYEE TO TEAMS
        activeEmployeeEntity.addTeam(teamAllEntity);
        activeEmployeeEntity.addTeam(teamBoysEntity);

        teamRepository.saveAll(List.of(teamBoysEntity, teamAllEntity));

        teamBoysEntity = teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = teamRepository.findByName("TEAM_ALL");

        Assertions.assertTrue(teamBoysEntity.getEmployees().contains(activeEmployeeEntity));
        Assertions.assertTrue(teamAllEntity.getEmployees().contains(activeEmployeeEntity));

        ActiveEmployeeEntity activeEmployee2 = ActiveEmployeeEntity.builder().name("").build();
    }
}