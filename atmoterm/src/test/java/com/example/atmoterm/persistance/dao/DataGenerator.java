package com.example.atmoterm.persistance.dao;

import java.util.List;

import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;

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
    }

    public static void save5EmployeesAnd3ActiveEmployeesIn3Teams(EmployeeEntity employee1,
        EmployeeEntity employee2,
        EmployeeEntity employee3,
        EmployeeEntity employee4,
        EmployeeEntity employee5,
        ActiveEmployeeEntity employee6,
        ActiveEmployeeEntity employee7,
        ActiveEmployeeEntity employee8,
        TeamEntity teamGirlsEntity,
        TeamEntity teamBoysEntity,
        TeamEntity teamAllEntity,
        EmployeeRepository employeeRepository,
        ActiveEmployeeRepository activeEmployeeRepository,
        TeamRepository teamRepository) {

        employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
        activeEmployeeRepository.saveAll(List.of(employee6, employee7, employee8));
        teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));

        employee1 = employeeRepository.findByName(employee1.getName());
        employee2 = employeeRepository.findByName(employee2.getName());
        employee3 = employeeRepository.findByName(employee3.getName());
        employee4 = employeeRepository.findByName(employee4.getName());
        employee5 = employeeRepository.findByName(employee5.getName());
        employee6 = activeEmployeeRepository.findByName(employee6.getName());
        employee7 = activeEmployeeRepository.findByName(employee7.getName());
        employee8 = activeEmployeeRepository.findByName(employee8.getName());

        employee2.addTeam(teamGirlsEntity);
        employee4.addTeam(teamGirlsEntity);
        employee6.addTeam(teamGirlsEntity);
        employee7.addTeam(teamGirlsEntity);

        employee1.addTeam(teamBoysEntity);
        employee3.addTeam(teamBoysEntity);
        employee5.addTeam(teamBoysEntity);
        employee8.addTeam(teamBoysEntity);

        employee1.addTeam(teamAllEntity);
        employee2.addTeam(teamAllEntity);
        employee3.addTeam(teamAllEntity);
        employee4.addTeam(teamAllEntity);
        employee5.addTeam(teamAllEntity);
        employee6.addTeam(teamAllEntity);
        employee7.addTeam(teamAllEntity);
        employee8.addTeam(teamAllEntity);

        teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));
    }
}
