package net.dni.job.listener;

import lombok.extern.log4j.Log4j2;
import net.dni.job.repository.EmployeeRepositoryImpl;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Autowired
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("starting - [{}]", jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("finishing - [{}]", jobExecution);
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            employeeRepositoryImpl.findAll().forEach(i -> log.info("<" + i + ">"));
        }
    }
}
