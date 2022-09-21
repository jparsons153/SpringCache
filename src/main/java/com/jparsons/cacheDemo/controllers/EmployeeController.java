package com.jparsons.cacheDemo.controllers;

import com.jparsons.cacheDemo.models.Employee;
import com.jparsons.cacheDemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    // Mapping to add a new Employee to the Repository
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    // Mapping to get all the Employees from the Repository
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Mapping to find an Employee from the Repository via Employee ID
    @GetMapping("employees/{employeeId}")
    public Employee findEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        System.out.println("Employee fetching from database:: " + employeeId);
        return employeeService.getEmployee(employeeId);
    }

    // Mapping to update an existing Employee in the Repository
    @PutMapping("employees/{employeeId}")
    public Employee updateEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                   @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.getEmployee(employeeId);
        employee.setName(employeeDetails.getName());
        return employeeService.saveEmployee(employee);
    }

    // Mapping to delete an Employee from the Repository via Employee ID
    @DeleteMapping("employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}
