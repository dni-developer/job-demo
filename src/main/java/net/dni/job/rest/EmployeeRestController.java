package net.dni.job.rest;

import net.dni.job.entity.Employee;
import net.dni.job.repository.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {

    private final EmployeeRepositoryImpl employeeRepositoryImpl;

    @Autowired
    public EmployeeRestController(EmployeeRepositoryImpl employeeRepositoryImpl) {
        this.employeeRepositoryImpl = employeeRepositoryImpl;
    }

    @GetMapping(path = "/employee/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepositoryImpl.findById(id).orElse(null);
    }
}
