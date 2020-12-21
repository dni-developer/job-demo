package net.dni.job.repository;

import net.dni.job.dao.EmployeeDao;
import net.dni.job.dao.EmployeeEsDao;
import net.dni.job.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeeEsDao employeeEsDao;

    public Optional<Employee> findById(Long id) {
        return employeeDao.findById(id);
    }

    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    public void save(Employee employee) {
        employeeDao.save(employee);
        employeeEsDao.save(employee);
    }
}
