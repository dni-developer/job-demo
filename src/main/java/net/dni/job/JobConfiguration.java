package net.dni.job;

import net.dni.job.entity.Employee;
import net.dni.job.function.ConvertToEmployeeFunction;
import net.dni.job.item.EmployeeCsvItem;
import net.dni.job.listener.JobCompletionNotificationListener;
import net.dni.job.task.EmployeeCsvDeleteTask;
import net.dni.job.task.EmployeeCsvDownloadTask;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Job importEmployeeCsvJob(
            JobCompletionNotificationListener listener,
            Step employeeCsvDownloadStep,
            Step importEmployeeFromCsvToDatabaseStep,
            Step employeeCsvDeleteStep) {
        return jobBuilderFactory.get("importEmployeeCsvJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(employeeCsvDownloadStep)
                .next(importEmployeeFromCsvToDatabaseStep)
                .next(employeeCsvDeleteStep)
                .end()
                .build();
    }

    @Bean
    public Step employeeCsvDownloadStep(EmployeeCsvDownloadTask employeeCsvDownloadTask) {
        return stepBuilderFactory.get("employeeCsvDownloadStep")
                .tasklet(employeeCsvDownloadTask)
                .build();
    }

    @Bean
    public Step employeeCsvDeleteStep(EmployeeCsvDeleteTask task) {
        return stepBuilderFactory.get("employeeCsvDeleteStep")
                .tasklet(task)
                .build();
    }

    @Bean
    public Step importEmployeeFromCsvToDatabaseStep(
            Resource downloadCsvFileResource,
            ItemWriter<Employee> employeeItemWriter,
            ItemProcessor<EmployeeCsvItem, Employee> employeeCsvItemProcessor) {
        return stepBuilderFactory.get("importEmployeeFromCsvToDatabaseStep")
                .<EmployeeCsvItem, Employee>chunk(1)
                .reader(employeeCSVFlatFileItemReader(downloadCsvFileResource))
                .processor(employeeCsvItemProcessor)
                .writer(employeeItemWriter)
                .build();
    }

    @Bean
    public ItemReader<EmployeeCsvItem> employeeCSVFlatFileItemReader(Resource resource) {
        return new FlatFileItemReaderBuilder<EmployeeCsvItem>()
                .name("employeeCSVFlatFileItemReader")
                .resource(resource)
                .delimited()
                .names("firstName", "lastName", "position")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(EmployeeCsvItem.class);
                }}).build();
    }

    @Bean
    public FunctionItemProcessor<EmployeeCsvItem, Employee> employeeCsvItemProcessor(ConvertToEmployeeFunction convertToEmployeeFunction) {
        return new FunctionItemProcessor<>(convertToEmployeeFunction);
    }

    @Bean
    public Resource downloadCsvFileResource() throws IOException {
        File file = File.createTempFile("download-employee", ".csv");
        return new FileUrlResource(file.getAbsolutePath());
    }
}
