package com.example.atmoterm.persistance.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "TEAM")
@Validated
public class TeamEntity extends AbstractEntity {

    @Column(name = "NAME")
    @NotBlank
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @Builder.Default
    @Column(name = "EMPLOYEES")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "TEAM_TO_EMPLOYEE",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private List<EmployeeEntity> employees = new ArrayList<>();

    public void removeEmployee(EmployeeEntity employee){
        this.employees.remove(employee);
        employee.getTeams().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TeamEntity))
            return false;

        TeamEntity other = (TeamEntity) o;

        return super.getId() != null &&
                super.getId().equals(other.getId()) && this.name.equals(other.getName());
    }

}
