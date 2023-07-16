package com.example.atmoterm.common.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.atmoterm.persistance.entity.AbstractEntity;
import com.example.atmoterm.persistance.entity.ActiveEmployeeEntity;
import com.example.atmoterm.persistance.entity.EmployeeEntity;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.service.to.EmployeeTo;

public class Mapper {

    public static EmployeeTo map2To(EmployeeEntity employee){
        return EmployeeTo.builder()
                .id(employee.getId())
                .versionNumber(employee.getVersionNumber())
                .name(employee.getName())
                .teamIDs(employee.getTeams().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
            .errorList(new ArrayList<>())
                .build();
    }

    public static List<EmployeeTo> mapEmployees2Tos(List<EmployeeEntity> entityList){
        return entityList.stream().map(Mapper::map2To).collect(Collectors.toList());
    }

    public static EmployeeEntity map2Entity(EmployeeTo employeeTo){
        return EmployeeEntity.builder()
                .id(employeeTo.getId())
                .name(employeeTo.getName())
                .build();
    }

    public static ActiveEmployeeTo map2To(ActiveEmployeeEntity activeEmployee){
        return ActiveEmployeeTo.builder()
                .id(activeEmployee.getId())
                .versionNumber(activeEmployee.getVersionNumber())
                .salary(activeEmployee.getSalary())
                .hireDate(activeEmployee.getHireDate())
                .name(activeEmployee.getName())
                .teamIDs(activeEmployee.getTeams().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
            .errorList(new ArrayList<>())
                .build();
    }

    public static List<ActiveEmployeeTo> mapActiveEmployees2Tos(List<ActiveEmployeeEntity> entityList){
        return entityList.stream().map(Mapper::map2To).collect(Collectors.toList());
    }

    public static ActiveEmployeeEntity map2Entity(ActiveEmployeeTo employeeTo){
        return ActiveEmployeeEntity.builder()
                .id(employeeTo.getId())
                .name(employeeTo.getName())
                .salary(employeeTo.getSalary())
                .hireDate(employeeTo.getHireDate())
                .build();
    }

}
