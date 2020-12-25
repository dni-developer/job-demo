package net.dni.job.service;

import org.springframework.core.io.Resource;

import java.io.File;

public interface EmployeeService {

    File downloadEmployeeCsv(Resource resource);

}
