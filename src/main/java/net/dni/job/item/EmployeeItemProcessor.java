package net.dni.job.item;

import lombok.extern.log4j.Log4j2;
import net.dni.job.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmployeeItemProcessor implements ItemProcessor<EmployeeCsvItem, Employee> {

    @Override
    public Employee process(EmployeeCsvItem employeeCSVItem) throws Exception {
        Employee employee = new Employee(null, employeeCSVItem.getFirstName().toUpperCase(), employeeCSVItem.getLastName().toUpperCase(), employeeCSVItem.getPosition().toUpperCase(), "CSV");
        log.info("convert " + employeeCSVItem + " to " + employee);
        return employee;
    }

}
