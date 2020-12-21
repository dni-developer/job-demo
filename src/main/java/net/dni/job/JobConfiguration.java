package net.dni.job;

import net.dni.job.entity.Employee;
import net.dni.job.item.EmployeeCsvItem;
import net.dni.job.listener.JobCompletionNotificationListener;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job importEmployeeCsvJob(
            JobCompletionNotificationListener listener,
            Step importEmployeeFromCsvToDatabaseStep) {
        return jobBuilderFactory.get("importEmployeeCsvJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(importEmployeeFromCsvToDatabaseStep)
                .end()
                .build();
    }

    @Bean
    public Step importEmployeeFromCsvToDatabaseStep(
            Resource resource,
            ItemWriter<Employee> employeeItemWriter,
            ItemProcessor<EmployeeCsvItem, Employee> employeeItemProcessor) {
        return stepBuilderFactory.get("importEmployeeFromCsvToDatabaseStep")
                .<EmployeeCsvItem, Employee>chunk(1)
                .reader(employeeCSVFlatFileItemReader(resource))
                .processor(employeeItemProcessor)
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
                .fieldSetMapper(new BeanWrapperFieldSetMapper<EmployeeCsvItem>() {{
                    setTargetType(EmployeeCsvItem.class);
                }}).build();
    }

    @Bean
    public Resource sampleResource() {
        return new ClassPathResource("data-batch.csv");
    }

}
