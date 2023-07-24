package com.ramjava.spring.batch.techie.config;

import com.ramjava.spring.batch.techie.entity.Customer;
import com.ramjava.spring.batch.techie.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor // Since no other constructor defined, it will inject these fields
public class SpringBatchConfig {
    // Two factory class
    //private JobBuilderFactory jobBuilderFactory; //JobBuilderFactory is deprecated
    private JobBuilder jobBuilder;
    //private StepBuilderFactory stepBuilderFactory; //StepBuilderFactory is deprecated
    private StepBuilder stepBuilder;

    private CustomerRepository customerRepository;

    public SpringBatchConfig() {
    }

    // Reader bean
    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/resources/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // Ignore first line since it's the header
        itemReader.setLineMapper(lineMapper()); //
        return itemReader;
    }

    // Map CSV file to Customer object
    public LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(); // Create object
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false); //
        // Columns of the header
        lineTokenizer.setNames("id,", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>(); // map to Customer object
        fieldSetMapper.setTargetType(Customer.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomerProcessor processor() {
        return new CustomerProcessor();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        // Object of RepositoryItemWriter
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository); // write to database
        writer.setMethodName("save");
        return writer;
    }
    // Give Read, Process and Write to Step
    /*
    @Bean
    public Step step1() {
        // Process data as a chunk
        return stepBuilder.get("csv-step").<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();

    }
     */
    @Bean
    public Step step1() {
        // Process data as a chunk
        return new StepBuilder("csv-step")
                .<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();

    }

    // Give step object to job
    /*
    @Bean
    public Job job() {
        return jobBuilderFactory.get("importCustomers")
                // A job can have multiple step by next()
                .flow(step1())
                .end().build();
    }
    */
    @Bean
    public Job job() {
        return jobBuilder.flow(step1())
                .end().build();
    }

    // Concurrently run jobs (Asynchronous)
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10); // Ten threads run concurrently
        return asyncTaskExecutor;
    }
}
