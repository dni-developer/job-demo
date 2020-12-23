package net.dni.job.task;

import lombok.extern.log4j.Log4j2;
import net.dni.job.service.EmployeeService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EmployeeCsvDownloadTask implements Tasklet {

    @Qualifier("downloadCsvFileResource")
    @Autowired
    Resource resource;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        employeeService.downloadEmployeeCsv(resource);
        log.info("execute task {} {}", contribution, chunkContext);
        return RepeatStatus.FINISHED;
    }
}
