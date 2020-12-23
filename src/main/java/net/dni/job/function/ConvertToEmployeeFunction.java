package net.dni.job.function;

import lombok.extern.log4j.Log4j2;
import net.dni.job.entity.Employee;
import net.dni.job.item.EmployeeCsvItem;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Log4j2
@Component
public class ConvertToEmployeeFunction implements Function<EmployeeCsvItem, Employee> {
    @Override
    public Employee apply(EmployeeCsvItem employeeCsvItem) {
        Employee employee = new Employee(null, employeeCsvItem.getFirstName().toUpperCase(), employeeCsvItem.getLastName().toUpperCase(), employeeCsvItem.getPosition().toUpperCase(), "CSV");
        log.info("convert " + employeeCsvItem + " to " + employee);
        return employee;
    }
}
