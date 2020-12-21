package net.dni.job.dao;

import net.dni.job.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    List<Employee> findAll();
}
