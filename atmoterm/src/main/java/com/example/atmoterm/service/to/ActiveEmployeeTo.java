package com.example.atmoterm.service.to;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class ActiveEmployeeTo extends EmployeeTo {
    private Double salary;
    private LocalDate hireDate;

    @Override
    public String toString() {

        return "ActiveEmployeeTo{" +
            "id=" + this.id +
            ", versionNumber=" + this.versionNumber +
            ", name='" + this.name +
            ", salary=" + this.salary +
            ", hireDate=" + this.hireDate +
            '}';
    }
}
