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
import com.example.atmoterm.service.impl.ActiveEmployeeServiceImpl;
import com.example.atmoterm.service.to.ActiveEmployeeTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rest/employee/active")
@Slf4j
public class ActiveEmployeeManagementController {

    private final ActiveEmployeeServiceImpl activeEmployeeService;

    private final ObjectMapper objectMapper;

    public ActiveEmployeeManagementController(ActiveEmployeeServiceImpl activeEmployeeService,
        ObjectMapper objectMapper) {
        this.activeEmployeeService = activeEmployeeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/addEmployee")
    public ResponseEntity<Object> addEmployee(@RequestBody ActiveEmployeeTo employee)
        throws JsonProcessingException {
        String logText;

        if (EmployeeValidator.validateActiveEmployee(employee, CRUDS.CREATE)) {
            employee = this.activeEmployeeService.addEmployee(employee);
            logText = "\n\nSuccessfully added new employee:\n" + employee.toString() + "\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @PostMapping(value = "/updateEmployeeNameById")
    public ResponseEntity<Object> updateEmployeeById(@RequestBody ActiveEmployeeTo employee)
        throws JsonProcessingException {
        String logText;

        if (EmployeeValidator.validateActiveEmployee(employee, CRUDS.UPDATE)) {
            employee = this.activeEmployeeService.updateEmployeeById(employee);
            logText = "\n\nSuccessfully updated employee, new version:\n" + employee + "\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @PostMapping(value = "/removeEmployeeWithName")
    public ResponseEntity<Object> removeEmployeeWithName(@RequestBody ActiveEmployeeTo employee)
        throws JsonProcessingException {
        String logText;
        if (EmployeeValidator.validateNameField(employee, CRUDS.DELETE)) {
            employee = this.activeEmployeeService.removeEmployeeWithName(employee);
            logText = "\n\nSuccessfully deleted employee:\n" + employee.toString() + "\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList();
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @GetMapping(value = "/findEmployeeByName")
    public ResponseEntity<Object> findEmployeeByName(@RequestBody ActiveEmployeeTo employee)
        throws JsonProcessingException {
        String logText;
        if (EmployeeValidator.validateNameField(employee, CRUDS.SEARCH)) {
            employee = this.activeEmployeeService.findByName(employee);
            logText = "\n\nSuccessfully found employee by name:\n" + employee.toString() + "\n";
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
        List<ActiveEmployeeTo> allEmployees = this.activeEmployeeService.findAllEmployees();
        logText = "\n\nSuccessfully found all employees:\n" + allEmployees + "\n";
        log.info(logText);
        return ResponseEntity.ok(produceJson(allEmployees));
    }

    private String produceJson(Object o) throws JsonProcessingException {
        return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

}
