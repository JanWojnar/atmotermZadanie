package com.example.atmoterm.service.mapper;

import com.example.atmoterm.persistance.entity.AbstractEntity;
import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.service.to.EmployeeTo;

import java.util.stream.Collectors;

public class Mapper {

    public static EmployeeTo map2To(EmployeeEntity employee){
        return EmployeeTo.builder()
                .id(employee.getId())
                .name(employee.getName())
                .teamIDs(employee.getTeams().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public static ActiveEmployeeTo map2To(ActiveEmployeeEntity activeEmployee){
        return ActiveEmployeeTo.builder()
                .id(activeEmployee.getId())
                .name(activeEmployee.getName())
                .teamIDs(activeEmployee.getTeams().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
                .build();
    }
}
