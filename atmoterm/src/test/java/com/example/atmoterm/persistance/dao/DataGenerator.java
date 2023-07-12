package com.example.atmoterm.persistance.dao;

import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;

import java.util.List;

public class DataGenerator {

    public static void save5EmployeesIn3Teams(EmployeeEntity employee1,
                                              EmployeeEntity employee2,
                                              EmployeeEntity employee3,
                                              EmployeeEntity employee4,
                                              EmployeeEntity employee5,
                                              TeamEntity teamGirlsEntity,
                                              TeamEntity teamBoysEntity,
                                              TeamEntity teamAllEntity,
                                              EmployeeRepository employeeRepository,
                                              TeamRepository teamRepository){

        employeeRepository.saveAll(List.of(employee1,employee2,employee3,employee4,employee5));
        teamRepository.saveAll(List.of(teamGirlsEntity,teamBoysEntity,teamAllEntity));

        employee1 = employeeRepository.findByName("Jan");
        employee2 = employeeRepository.findByName("Zuzanna");
        employee3 = employeeRepository.findByName("Karol");
        employee4 = employeeRepository.findByName("Kasia");
        employee5 = employeeRepository.findByName("Marek");

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

        teamRepository.saveAll(List.of(teamGirlsEntity,teamBoysEntity,teamAllEntity));

        employee1 = employeeRepository.findByName("Jan");
        employee2 = employeeRepository.findByName("Zuzanna");
        employee3 = employeeRepository.findByName("Karol");
        employee4 = employeeRepository.findByName("Kasia");
        employee5 = employeeRepository.findByName("Marek");

        teamGirlsEntity = teamRepository.findByName("TEAM_GIRLS");
        teamBoysEntity = teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = teamRepository.findByName("TEAM_ALL");
    }
}
