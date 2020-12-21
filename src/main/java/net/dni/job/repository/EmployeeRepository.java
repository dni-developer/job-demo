package net.dni.job.repository;

import net.dni.job.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeRepository {
    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    void save(Employee employee);
}
