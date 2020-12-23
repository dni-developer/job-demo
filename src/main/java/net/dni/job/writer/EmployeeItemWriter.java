package net.dni.job.writer;

import net.dni.job.entity.Employee;
import net.dni.job.repository.EmployeeRepositoryImpl;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeItemWriter<T> implements ItemWriter<T>, InitializingBean {

    @Autowired
    EmployeeRepositoryImpl employeeRepositoryImpl;

    @Override
    public void write(List<? extends T> items) {
        for (T item : items) {
            if (item instanceof Employee) {
                employeeRepositoryImpl.save((Employee) item);
            } else {
                throw new RuntimeException("Not Employee");
            }
        }
    }

    @Override
    public void afterPropertiesSet() {

    }

}
