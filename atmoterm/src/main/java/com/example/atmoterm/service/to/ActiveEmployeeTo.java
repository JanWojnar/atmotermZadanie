package com.example.atmoterm.service.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class ActiveEmployeeTo extends EmployeeTo {
    private Double salary;
    private LocalDate hireDate;
}
