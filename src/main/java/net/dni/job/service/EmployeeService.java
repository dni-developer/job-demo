package net.dni.job.service;

import org.springframework.core.io.Resource;

public interface EmployeeService {

    void downloadEmployeeCsv(Resource resource);

}
