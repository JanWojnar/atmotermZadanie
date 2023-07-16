package com.example.atmoterm.testing;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.atmoterm.persistance.dao.ActiveEmployeeRepository;
import com.example.atmoterm.persistance.dao.EmployeeRepository;
import com.example.atmoterm.persistance.dao.TeamRepository;
import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
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

    public void init() {

        EmployeeEntity employee1 = EmployeeEntity.builder().name("Jan").build();
        EmployeeEntity employee2 = EmployeeEntity.builder().name("Zuzanna").build();
        EmployeeEntity employee3 = EmployeeEntity.builder().name("Karol").build();
        EmployeeEntity employee4 = EmployeeEntity.builder().name("Kasia").build();
        EmployeeEntity employee5 = EmployeeEntity.builder().name("Marek").build();

        ActiveEmployeeEntity employee6 = ActiveEmployeeEntity.builder()
            .name("Blazej")
            .hireDate(LocalDate.of(2004, 2, 2))
            .salary(1000.0)
            .build();
        ActiveEmployeeEntity employee7 = ActiveEmployeeEntity.builder()
            .name("Aneta")
            .hireDate(LocalDate.of(2003, 2, 2))
            .salary(2000.0)
            .build();
        ActiveEmployeeEntity employee8 = ActiveEmployeeEntity.builder()
            .name("Pawel")
            .hireDate(LocalDate.of(2002, 2, 2))
            .salary(3000.0)
            .build();
        ActiveEmployeeEntity employee9 = ActiveEmployeeEntity.builder()
            .name("Anna")
            .hireDate(LocalDate.of(2001, 2, 2))
            .salary(4000.0)
            .build();

        TeamEntity teamGirlsEntity = TeamEntity.builder().name("TEAM_GIRLS").build();
        TeamEntity teamBoysEntity = TeamEntity.builder().name("TEAM_BOYS").build();
        TeamEntity teamAllEntity = TeamEntity.builder().name("TEAM_ALL").build();

        this.employeeRepository.saveAll(List.of(employee1, employee2, employee3, employee4, employee5));
        this.activeEmployeeRepository.saveAll(List.of(employee6, employee7, employee8, employee9));
        this.teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));

        employee1 = this.employeeRepository.findByName("Jan");
        employee2 = this.employeeRepository.findByName("Zuzanna");
        employee3 = this.employeeRepository.findByName("Karol");
        employee4 = this.employeeRepository.findByName("Kasia");
        employee5 = this.employeeRepository.findByName("Marek");

        employee6 = this.activeEmployeeRepository.findByName("Blazej");
        employee7 = this.activeEmployeeRepository.findByName("Aneta");
        employee8 = this.activeEmployeeRepository.findByName("Pawel");
        employee9 = this.activeEmployeeRepository.findByName("Anna");

        employee2.addTeam(teamGirlsEntity);
        employee4.addTeam(teamGirlsEntity);
        employee7.addTeam(teamGirlsEntity);
        employee9.addTeam(teamGirlsEntity);

        employee1.addTeam(teamBoysEntity);
        employee3.addTeam(teamBoysEntity);
        employee5.addTeam(teamBoysEntity);
        employee6.addTeam(teamBoysEntity);
        employee8.addTeam(teamBoysEntity);

        employee1.addTeam(teamAllEntity);
        employee2.addTeam(teamAllEntity);
        employee3.addTeam(teamAllEntity);
        employee4.addTeam(teamAllEntity);
        employee5.addTeam(teamAllEntity);
        employee6.addTeam(teamAllEntity);
        employee7.addTeam(teamAllEntity);
        employee8.addTeam(teamAllEntity);
        employee9.addTeam(teamAllEntity);

        this.teamRepository.saveAll(List.of(teamGirlsEntity, teamBoysEntity, teamAllEntity));
        log.info("Example database initialized and populated!");
    }
}
