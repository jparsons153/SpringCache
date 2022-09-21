package com.jparsons.cacheDemo.controllers;

import com.jparsons.cacheDemo.models.Employee;
import com.jparsons.cacheDemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    // @Cacheable is used here to ensure that the return value of this method is stored into a cache named "employees"
    // Next time this method is called with the same parameters, it will be skipped and simply return the value from cache
    @GetMapping("employees/{employeeId}")
    @Cacheable(value = "employees", key = "#employeeId", condition = "#employeeId > 50")
    public Employee findEmployeeById(@PathVariable(value = "employeeId") Integer employeeId) {
        System.out.println("Employee fetching from database:: " + employeeId);
        return employeeService.getEmployee(employeeId);
    }

    // Here you can update the cache by using @CachePut
    // This makes sure the method is run and updates the value each time
    @PutMapping("employees/{employeeId}")
    @CachePut(value = "employees", key = "#employeeId")
    public Employee updateEmployee(@PathVariable(value = "employeeId") Integer employeeId,
                                   @RequestBody Employee employeeDetails) {
        Employee employee = employeeService.getEmployee(employeeId);
        employee.setName(employeeDetails.getName());
        return employeeService.saveEmployee(employee);
    }

    // This @CacheEvict clears the entire "employees" cache
    @DeleteMapping("employees/{id}")
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}
