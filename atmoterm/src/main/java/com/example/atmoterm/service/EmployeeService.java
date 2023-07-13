package com.example.atmoterm.service;

import java.util.List;

public interface EmployeeService<T> {
    T addEmployee(T employeeTo);

    T updateEmployeeById(T employeeTo);

    T removeEmployeeWithName(T employeeTo);

    T findByName(T employeeTo);

    List<T> findAllEmployees();
}
