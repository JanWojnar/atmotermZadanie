package com.example.atmoterm.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "ACTIVE_EMPLOYEE")
public class ActiveEmployeeEntity extends EmployeeEntity {

    @Column(name = "SALARY")
    @NotNull
    @Min(value = 0, message = "Salary should not be less than 0")
    private Double salary;

    @Column(name = "HIRE_DATE")
    @NotNull
    @PastOrPresent
    private LocalDate hireDate;

}
