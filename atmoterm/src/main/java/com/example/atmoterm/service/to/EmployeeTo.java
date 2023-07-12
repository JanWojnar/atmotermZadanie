package com.example.atmoterm.service.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
public class EmployeeTo {
    private Long id;
    private Long versionNumber;
    private String name;
    private List<Long> teamIDs = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeTo employee = (EmployeeTo) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, teamIDs, errorList);
    }

    @Override
    public String toString() {
        return "EmployeeTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
