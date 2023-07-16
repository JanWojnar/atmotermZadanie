package com.example.atmoterm.persistance.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.persistance.entity.TeamEntity;

@SpringBootTest
class RepositoryTest {

    @Autowired
    ActiveEmployeeRepository activeEmployeeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        this.teamRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.activeEmployeeRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        this.teamRepository.deleteAll();
        this.employeeRepository.deleteAll();
        this.activeEmployeeRepository.deleteAll();
    }

    @Test
    void savingEmployeesInMultipleSharedTeams() {

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
            this.employeeRepository, this.teamRepository);

        Assertions.assertEquals(employee1.getName(), this.employeeRepository.findByName("Jan").getName());
        Assertions.assertEquals(employee2.getName(), this.employeeRepository.findByName("Zuzanna").getName());
        Assertions.assertEquals(employee3.getName(), this.employeeRepository.findByName("Karol").getName());
        Assertions.assertEquals(employee4.getName(), this.employeeRepository.findByName("Kasia").getName());
        Assertions.assertEquals(employee5.getName(), this.employeeRepository.findByName("Marek").getName());

        Assertions.assertTrue(teamGirlsEntity.getEmployees().containsAll(List.of(employee2, employee4)));
        Assertions.assertTrue(teamBoysEntity.getEmployees().containsAll(List.of(employee1, employee3, employee5)));
        Assertions.assertTrue(teamAllEntity.getEmployees().containsAll(List.of(employee1, employee2, employee3, employee4, employee5)));
    }
    @Test
    void removingTeamShouldNotRemoveEmployeesTest() {
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
            this.employeeRepository, this.teamRepository);

        //REMOVING TEAM
        this.teamRepository.delete(this.teamRepository.findByName("TEAM_GIRLS"));
        Assertions.assertNull(this.teamRepository.findByName("TEAM_GIRLS"));

        //...DOES NOT REMOVE EMPLOYEES
        employee2 = this.employeeRepository.findByName("Zuzanna");
        employee4 = this.employeeRepository.findByName("Kasia");

        Assertions.assertNotNull(employee2);
        Assertions.assertNotNull(employee4);
        Assertions.assertFalse(employee2.getTeams().contains(teamGirlsEntity));
        Assertions.assertFalse(employee4.getTeams().contains(teamGirlsEntity));

    }
    @Test
    void removingOfEmployeeThatIsAssignedInTwoTeamsTest() {

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
            this.employeeRepository, this.teamRepository);

        //TO REMOVE EMPLOYEE FIRSTLY DELETE HIM/HER FROM TEAMS!
        EmployeeEntity employeeToBeDeleted = this.employeeRepository.findByName("Jan");
        List<TeamEntity> teamsToClearFromEmployee = new ArrayList<>(employeeToBeDeleted.getTeams());
        for (TeamEntity team : teamsToClearFromEmployee) {
            team.removeEmployee(employeeToBeDeleted);
            this.teamRepository.save(team);
        }
        this.employeeRepository.delete(employeeToBeDeleted);

        teamBoysEntity = this.teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = this.teamRepository.findByName("TEAM_ALL");
        Assertions.assertFalse(teamAllEntity.getEmployees().contains(employeeToBeDeleted));
        Assertions.assertFalse(teamBoysEntity.getEmployees().contains(employeeToBeDeleted));
        Assertions.assertNull(this.employeeRepository.findByName("Jan"));
    }

        @Test
        void addingActiveEmployeeToTeamsWithEmployeeOfTypeEmployeeEntity(){

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
            this.employeeRepository, this.teamRepository);

        //ADDING ACTIVEEMPLOYEE
        ActiveEmployeeEntity activeEmployeeEntity = ActiveEmployeeEntity.builder().name("Bob").salary(9999.0).hireDate(LocalDate.of(2000,11,12)).build();
        activeEmployeeEntity = this.activeEmployeeRepository.save(activeEmployeeEntity);

        teamBoysEntity = this.teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = this.teamRepository.findByName("TEAM_ALL");

        //ADDING ACTIVEEMPLOYEE TO TEAMS
        activeEmployeeEntity.addTeam(teamAllEntity);
        activeEmployeeEntity.addTeam(teamBoysEntity);

        this.teamRepository.saveAll(List.of(teamBoysEntity, teamAllEntity));

        teamBoysEntity = this.teamRepository.findByName("TEAM_BOYS");
        teamAllEntity = this.teamRepository.findByName("TEAM_ALL");

        Assertions.assertTrue(teamBoysEntity.getEmployees().contains(activeEmployeeEntity));
        Assertions.assertTrue(teamAllEntity.getEmployees().contains(activeEmployeeEntity));
    }

    @Test
    void employeeValidationTest(){

        ActiveEmployeeEntity badSalaryEmployee = ActiveEmployeeEntity.builder()
                .name("Karol")
                .hireDate(LocalDate.of(2000,2,2))
                .salary(-0.5)
                .build();
        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class,
            () -> this.activeEmployeeRepository.save(badSalaryEmployee));

        ActiveEmployeeEntity badHireDateEmployee = ActiveEmployeeEntity.builder()
                .name("Karol")
                .hireDate(LocalDate.of(3000,2,2))
                .salary(4000.0)
                .build();
        Assertions.assertThrows(ConstraintViolationException.class,
            () -> this.activeEmployeeRepository.save(badHireDateEmployee));

        ActiveEmployeeEntity shortNameEmployee = ActiveEmployeeEntity.builder()
                .name("")
                .hireDate(LocalDate.of(2000,2,2))
                .salary(4000.0)
                .build();
        Assertions.assertThrows(ConstraintViolationException.class,
            () -> this.activeEmployeeRepository.save(shortNameEmployee));

        ActiveEmployeeEntity longNameEmployee = ActiveEmployeeEntity.builder()
                .name("x".repeat(51))
                .hireDate(LocalDate.of(2000,2,2))
                .salary(4000.0)
                .build();
        Assertions.assertThrows(ConstraintViolationException.class,
            () -> this.activeEmployeeRepository.save(longNameEmployee));
    }

    @Test
    void teamValidationTest(){
        TeamEntity nameTooShort = TeamEntity.builder().name("").build();
        Assertions.assertThrows(ConstraintViolationException.class,
            () -> this.teamRepository.save(nameTooShort));

        TeamEntity nameTooLong = TeamEntity.builder().name("x".repeat(51)).build();
        Assertions.assertThrows(ConstraintViolationException.class,
            () -> this.teamRepository.save(nameTooLong));
    }
}