package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.decider.MyJobExecutionDecider;
import com.springbatch.listener.MyStepExecutionListener;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	
	// Example of Sequential Flow
	/*
	@Bean
	public Job firstJob() {
		return this.jobBuilderFactory.get("Job1").preventRestart()
				.start(step3()).next(step2()).next(step1()).build();
	}*/
	
	// Example of conditional flow part 1
	/*
	@Bean
	public Job firstJob() {
		return jobBuilderFactory.get("Job1").
				start(step1()).on("COMPLETED").to(step2()).
				from(step2()).on("COMPLETED").to(step3()).
				end().build();
	}
	*/
	
	
	// StepExecutionListener Implementation
	
	/*@Bean
	public StepExecutionListener myStepExecutionListener() {
		return new MyStepExecutionListener();
	}
	
	
	@Bean
	public Step step2() {
		return this.stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				boolean isFailure = false;
				if(isFailure) {
					throw new Exception("Test Exception in Step 2");
				}
				System.out.println("Executing Step 2...........");
				return RepeatStatus.FINISHED;
			}
		}).listener(myStepExecutionListener()).build();
	}
	
	@Bean
		public Job firstJob() {
			return jobBuilderFactory.get("Job1").
					start(step1()).on("COMPLETED").to(step2()).
					from(step2()).on("TEST_STATUS").to(step3()).
					from(step2()).on("*").to(step4()).
					end().
					build();
	    }
	    
	 // JobExecutionDecider Implementation
	  @Bean
		public Job firstJob() {
			return jobBuilderFactory.get("Job1").start(step1()).on("COMPLETED")
					.to(decider()).on("TEST_STATUS").to(step2()).from(decider())
					.on("*").to(step3()).end().build();
	    }
	*/
	
	@Bean
	public JobExecutionDecider decider() {
		return new MyJobExecutionDecider();
	}
	
	
	// Example of conditional flow part 2
		@Bean
		public Job firstJob() {
			return jobBuilderFactory.get("Job1").start(step1()).on("COMPLETED")
					.to(decider()).on("TEST_STATUS").to(step2()).from(decider())
					.on("*").to(step3()).end().build();
	    }
	
	@Bean
	public Step step1() {
		return this.stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing Step 1.............");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step2() {
		return this.stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				boolean isFailure = false;
				if(isFailure) {
					throw new Exception("Test Exception in Step 2");
				}
				System.out.println("Executing Step 2...........");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step3() {
		return this.stepBuilderFactory.get("step3").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 3.............");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step step4() {
		return this.stepBuilderFactory.get("step4").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 4.............");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
}
