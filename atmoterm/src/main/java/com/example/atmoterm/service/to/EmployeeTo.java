package com.example.atmoterm.service.to;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class EmployeeTo {
    protected Long id;

    protected Long versionNumber;

    protected String name;

    protected List<Long> teamIDs = new ArrayList<>();

    protected List<String> errorList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTo employee = (EmployeeTo) o;
        return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.teamIDs, this.errorList);
    }

    @Override
    public String toString() {
        return "EmployeeTo{" +
                "id=" + this.id +
            ", versionNumber=" + this.versionNumber +
                ", name='" + this.name + '\'' +
                '}';
    }
}
