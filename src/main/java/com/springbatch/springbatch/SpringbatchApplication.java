package com.springbatch.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringbatchApplication {

/*	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private BatchProperties.Job fileToRestApiJob;*/

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

	/*@PostConstruct
	public void runBatchJob() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("source", "fileToRestApiJob")
				.toJobParameters();
		jobLauncher.run((Job) fileToRestApiJob, jobParameters);
	}*/

	@Bean
	CommandLineRunner run(JobLauncher jobLauncher, Job job) {
		return args -> {
			jobLauncher.run(job, new JobParameters());
		};
	}

}
