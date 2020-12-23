package net.dni.job.rest;

import net.dni.job.entity.Employee;
import net.dni.job.repository.EmployeeRepository;
import net.dni.job.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping(path = "/employee/{id}")
    public Employee findById(@PathVariable Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
