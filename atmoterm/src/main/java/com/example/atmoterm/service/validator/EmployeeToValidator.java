package com.example.atmoterm.service.validator;

import com.example.atmoterm.service.to.EmployeeTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeToValidator {

    public static boolean validateNameField(EmployeeTo employeeTo) {
        boolean valid = true;
        List<String> errorList = new ArrayList<>();
        if(Objects.isNull(employeeTo.getName())){
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `name` field!");
        } else if(employeeTo.getName().length()>50 || employeeTo.getName().length()==0){
            valid = false;
            employeeTo.getErrorList().add("Provided 'name' must have between 1 and 50 characters!");
        }
        return valid;
    }

    public static boolean validateIDField(EmployeeTo employeeTo) {
        boolean valid = true;
        List<String> errorList = new ArrayList<>();
        if(Objects.isNull(employeeTo.getId())){
            valid = false;
            employeeTo.getErrorList().add("Provided JSON does not include `ID` field!");
        } else if(employeeTo.getId()<=0L){
            valid = false;
            employeeTo.getErrorList().add("Provided 'ID' must be bigger than 0!");
        }
        return valid;
    }

}
