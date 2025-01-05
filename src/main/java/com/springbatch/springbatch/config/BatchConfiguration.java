package com.springbatch.springbatch.config;

import com.springbatch.springbatch.Writer.RestApiItemWriter;
import com.springbatch.springbatch.dto.MemberDto;
import com.springbatch.springbatch.processor.MemberItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("fileToRestApiJob")
                .start(fileToRestApiStep())
                .build();
    }



    @Bean
    public Step fileToRestApiStep() {
        return stepBuilderFactory.get("fileToRestApiStep")
                .<MemberDto, MemberDto>chunk(100)  // 100 records per chunk for efficient processing
                .reader(fileItemReader())       // Read records from file
                .processor(myRecordProcessor()) // Optional transformation or validation
                .writer(restApiItemWriter())    // Write (send to REST API)
                .build();
    }

    @Bean
    public ItemReader<MemberDto> fileItemReader() {
        return new FlatFileItemReaderBuilder<MemberDto>()
                .name("fileItemReader")
                .resource(new ClassPathResource("sample_data.txt"))
                .linesToSkip(1) // Skip the header row
                .delimited()
                .names(new String[] {"id", "firstName","lastName","jobTitle","team","status"}) // Define the columns in your file
                .targetType(MemberDto.class)
                .build();
    }

    @Bean
    public ItemProcessor<MemberDto, MemberDto> myRecordProcessor() {
        return new MemberItemProcessor();  // Optional processing logic
    }

    @Bean
    public ItemWriter<MemberDto> restApiItemWriter() {
        return new RestApiItemWriter(restTemplate);  // Send records to the third-party API
    }
}
