package com.example.atmoterm.persistance.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class EmployeeEntity extends AbstractEntity {

    @Column(name = "NAME")
    @NotBlank
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;


    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    @Builder.Default
    private List<TeamEntity> teams = new ArrayList<>();

    public void addTeam(TeamEntity teamEntity){
        this.teams.add(teamEntity);
        teamEntity.getEmployees().add(this);
    }

    public void removeTeam(TeamEntity teamEntity){
        this.teams.remove(teamEntity);
        teamEntity.getEmployees().remove(this);
    }

    public void singOffTeams(){
        teams.forEach(teamEntity -> teamEntity.getEmployees().remove(this));
        this.teams.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity other = (EmployeeEntity) o;
        return super.getId() != null &&
                super.getId().equals(other.getId()) && this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, teams);
    }
}
