package com.example.atmoterm.service.controller;

import com.example.atmoterm.service.validator.EmployeeToValidator;
import com.example.atmoterm.service.impl.EmployeeServiceImpl;
import com.example.atmoterm.service.to.EmployeeTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
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
        if(EmployeeToValidator.validateNameField(employee)){
            employee = this.employeeService.addEmployee(employee);
            logText = "\n\nSuccessfully added new employee:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList().get(0);
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @PostMapping(value = "/updateEmployeeNameById")
    public ResponseEntity<Object> updateEmployeeNameById(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;

        EmployeeToValidator.validateNameField(employee);
        EmployeeToValidator.validateIDField(employee);

        if(employee.getErrorList().isEmpty()){
            employee = this.employeeService.updateEmployeeNameById(employee);
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
        if(EmployeeToValidator.validateNameField(employee)){
            employee = this.employeeService.removeEmployeeWithName(employee);
            logText = "\n\nSuccessfully deleted employee:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList().get(0);
            log.warn(logText);
            return ResponseEntity.badRequest().body(produceJson(employee.getErrorList()));
        }
        return ResponseEntity.ok(produceJson(employee));
    }

    @GetMapping(value = "/findEmployeeByName")
    public ResponseEntity<Object> findEmployeeByName(@RequestBody EmployeeTo employee) throws JsonProcessingException {
        String logText;
        if(EmployeeToValidator.validateNameField(employee)){
            employee = this.employeeService.findByName(employee);
            logText = "\n\nSuccessfully found employee by name:\n" + employee.toString()+"\n";
            log.info(logText);
        } else {
            logText = "Bad input JSON: " + employee.getErrorList().get(0);
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
