package net.dni.job.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;

@Log4j2
@Component
public class EmployeeCsvDeleteTask implements Tasklet {

    @Qualifier("downloadCsvFileResource")
    @Autowired
    Resource resource;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File file = resource.getFile();
        String path = file.getAbsolutePath();
        if (resource.getFile().delete()) {
            log.info("success delete - [{}]", path);
        } else {
            log.info("failed to delete - [{}]", path);
        }
        log.info("execute task {} {}", contribution, chunkContext);
        return RepeatStatus.FINISHED;
    }
}
