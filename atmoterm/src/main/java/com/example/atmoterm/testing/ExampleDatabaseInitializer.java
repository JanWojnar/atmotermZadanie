package com.example.atmoterm.testing;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.dao.EmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("exampleDatabase")
@Slf4j
public class ExampleDatabaseInitializer {

    private final EmployeeRepository employeeRepository;

    private final ActiveEmployeeRepository activeEmployeeRepository;

    private final TeamRepository teamRepository;

    public ExampleDatabaseInitializer(EmployeeRepository employeeRepository,
        ActiveEmployeeRepository activeEmployeeRepository, TeamRepository teamRepository) {

        this.employeeRepository = employeeRepository;
        this.activeEmployeeRepository = activeEmployeeRepository;
        this.teamRepository = teamRepository;

        init();
    }

    private void init() {

        EmployeeEntity employee1 = EmployeeEntity.builder().name("Jan").build();
        EmployeeEntity employee2 = EmployeeEntity.builder().name("Zuzanna").build();
        EmployeeEntity employee3 = EmployeeEntity.builder().name("Karol").build();
        EmployeeEntity employee4 = EmployeeEntity.builder().name("Kasia").build();
        EmployeeEntity employee5 = EmployeeEntity.builder().name("Marek").build();

        TeamEntity teamGirlsEntity = TeamEntity.builder().name("TEAM_GIRLS").build();
        TeamEntity teamBoysEntity = TeamEntity.builder().name("TEAM_BOYS").build();
        TeamEntity teamAllEntity = TeamEntity.builder().name("TEAM_ALL").build();

        this.employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
        this.teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));

        employee1 = this.employeeRepository.findByName("Jan");
        employee2 = this.employeeRepository.findByName("Zuzanna");
        employee3 = this.employeeRepository.findByName("Karol");
        employee4 = this.employeeRepository.findByName("Kasia");
        employee5 = this.employeeRepository.findByName("Marek");

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

        this.teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));

        employee1 = this.employeeRepository.findByName("Jan");
        employee2 = this.employeeRepository.findByName("Zuzanna");
        employee3 = this.employeeRepository.findByName("Karol");
        employee4 = this.employeeRepository.findByName("Kasia");
        employee5 = this.employeeRepository.findByName("Marek");

        teamGirlsEntity = this.teamRepository.findByName("TEAM_GIRLS");
        teamBoysEntity = this.teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = this.teamRepository.findByName("TEAM_ALL");

        log.info("Example database initialized!");
    }
}
