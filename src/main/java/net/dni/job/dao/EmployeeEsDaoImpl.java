package net.dni.job.dao;

import lombok.extern.log4j.Log4j2;
import net.dni.job.entity.Employee;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class EmployeeEsDaoImpl implements EmployeeEsDao {
    @Override
    public void save(Employee employee) {
        log.info("ES insert - [{}]", employee);
    }
}
