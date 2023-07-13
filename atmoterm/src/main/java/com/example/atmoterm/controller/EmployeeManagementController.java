package com.example.atmoterm.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.atmoterm.common.constant.CRUDS;
import com.example.atmoterm.common.validator.EmployeeValidator;
import com.example.atmoterm.service.impl.EmployeeServiceImpl;
import com.example.atmoterm.service.to.EmployeeTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest/employee")
@Slf4j
public class EmployeeManagementController {

    private final EmployeeServiceImpl employeeService;

    private final ObjectMapper objectMapper;

    public EmployeeManagementController(EmployeeServiceImpl employeeService, ObjectMapper objectMapper){
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/addEmployee")
    public ResponseEntity<Object> addEmployee(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;
        if (EmployeeValidator.validateNameField(employee, CRUDS.CREATE)) {
            employee = this.employeeService.addEmployee(employee);
            logText = "\n\nSuccessfully added new employee:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @PostMapping(value = "/updateEmployeeNameById")
    public ResponseEntity<Object> updateEmployeeNameById(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;

        EmployeeValidator.validateIDField(employee);
        EmployeeValidator.validateNameField(employee, CRUDS.UPDATE);

        if (employee.getErrorList().isEmpty()) {
            employee = this.employeeService.updateEmployeeById(employee);
            logText = "\n\nSuccessfully updated employee, new version:\n" + employee +"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @PostMapping(value = "/removeEmployeeWithName")
    public ResponseEntity<Object> removeEmployeeWithName(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;
        if (EmployeeValidator.validateNameField(employee, CRUDS.DELETE)) {
            employee = this.employeeService.removeEmployeeWithName(employee);
            logText = "\n\nSuccessfully deleted employee:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @GetMapping(value = "/findEmployeeByName")
    public ResponseEntity<Object> findEmployeeByName(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;
        if (EmployeeValidator.validateNameField(employee, CRUDS.SEARCH)) {
            employee = this.employeeService.findByName(employee);
            logText = "\n\nSuccessfully found employee by name:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @GetMapping(value = "/findAllEmployees")
    public ResponseEntity<Object> findAllEmployees() throws JsonProcessingException {
        String logText;
        List<EmployeeTo> allEmployees = this.employeeService.findAllEmployees();
        logText = "\n\nSuccessfully found all employees:\n" + allEmployees+"\n";
        log.info(logText);
        return ResponseEntity.ok(produceJson(allEmployees));
    }

    @GetMapping(value = "/findInactiveEmployees")
    public ResponseEntity<Object> findInactiveEmployees() throws JsonProcessingException {
        String logText;
        List<EmployeeTo> findInactiveEmployees = this.employeeService.findInactiveEmployees();
        logText = "\n\nSuccessfully found all inactive employees:\n" + findInactiveEmployees+"\n";
        log.info(logText);
        return ResponseEntity.ok(produceJson(findInactiveEmployees));
    }

    private String produceJson(Object o) throws JsonProcessingException {
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }


}
