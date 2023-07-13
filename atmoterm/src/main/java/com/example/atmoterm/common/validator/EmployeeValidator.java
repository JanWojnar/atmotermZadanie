package com.example.atmoterm.common.validator;

import java.time.LocalDate;
import java.util.Objects;

import com.example.atmoterm.common.constant.CRUDS;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.example.atmoterm.service.to.EmployeeTo;

public class EmployeeValidator {

    public static boolean validateIDField(EmployeeTo employeeTo) {
        boolean valid = true;
        if (Objects.isNull(employeeTo.getId())) {
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `ID` field!");
        } else if (employeeTo.getId() <= 0L) {
            valid = false;
            employeeTo.getErrorList().add("Provided 'ID' must be bigger than 0!");
        }
        return valid;
    }

    public static boolean validateNameField(EmployeeTo employeeTo, CRUDS type) {
        boolean valid = true;
        if (Objects.isNull(employeeTo.getName()) && type.equals(CRUDS.CREATE)) {
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `name` field!");
        } else if (Objects.nonNull(employeeTo.getName())
            && (employeeTo.getName().length() > 50 || employeeTo.getName().length() == 0)) {
            valid = false;
            employeeTo.getErrorList().add("Provided 'name' must have between 1 and 50 characters!");
        }
        return valid;
    }

    public static boolean validateSalaryField(ActiveEmployeeTo employeeTo, CRUDS type) {
        boolean valid = true;
        if (Objects.isNull(employeeTo.getSalary()) && type.equals(CRUDS.CREATE)) {
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `salary` field!");
        } else if (Objects.nonNull(employeeTo.getSalary()) && employeeTo.getSalary() <= 0) {
            valid = false;
            employeeTo.getErrorList().add("Salary value must be positive!");
        }
        return valid;
    }

    public static boolean validateHireDateField(ActiveEmployeeTo employeeTo, CRUDS type) {
        boolean valid = true;
        if (Objects.isNull(employeeTo.getSalary()) && type.equals(CRUDS.CREATE)) {
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `hireDate` field!");
        } else if (Objects.nonNull(employeeTo.getHireDate())
            && employeeTo.getHireDate().isAfter(LocalDate.now())) {
            valid = false;
            employeeTo.getErrorList().add("Hire date must be present or past!");
        }
        return valid;
    }

    public static boolean validateActiveEmployee(ActiveEmployeeTo activeEmployeeTo, CRUDS type) {
        validateNameField(activeEmployeeTo, type);
        validateHireDateField(activeEmployeeTo, type);
        validateSalaryField(activeEmployeeTo, type);
        return activeEmployeeTo.getErrorList().isEmpty();
    }

}
