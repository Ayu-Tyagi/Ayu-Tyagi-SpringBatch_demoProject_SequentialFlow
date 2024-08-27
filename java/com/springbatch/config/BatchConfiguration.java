package com.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.decider.MyJobExecutionDecider;
import com.springbatch.listener.MyJobExecutionListener;
import com.springbatch.listener.MyStepExecutionListener;

@Configuration
public class BatchConfiguration {

	// Example of Sequential Flow
	/*
	 * @Bean public Job firstJob() { return
	 * this.jobBuilderFactory.get("Job1").preventRestart()
	 * .start(step3()).next(step2()).next(step1()).build(); }
	 */

	// Example of conditional flow part 1
	/*
	 * @Bean public Job firstJob() { return jobBuilderFactory.get("Job1").
	 * start(step1()).on("COMPLETED").to(step2()).
	 * from(step2()).on("COMPLETED").to(step3()). end().build(); }
	 */

	// StepExecutionListener Implementation

	/*
	 * @Bean public StepExecutionListener myStepExecutionListener() { return new
	 * MyStepExecutionListener(); }
	 * 
	 * 
	 * @Bean public Step step2() { return
	 * this.stepBuilderFactory.get("step2").tasklet(new Tasklet() {
	 * 
	 * @Override public RepeatStatus execute(StepContribution contribution,
	 * ChunkContext chunkContext) throws Exception { // TODO Auto-generated method
	 * stub boolean isFailure = false; if(isFailure) { throw new
	 * Exception("Test Exception in Step 2"); }
	 * System.out.println("Executing Step 2..........."); return
	 * RepeatStatus.FINISHED; } }).listener(myStepExecutionListener()).build(); }
	 * 
	 * @Bean public Job firstJob() { return jobBuilderFactory.get("Job1").
	 * start(step1()).on("COMPLETED").to(step2()).
	 * from(step2()).on("TEST_STATUS").to(step3()).
	 * from(step2()).on("*").to(step4()). end(). build(); }
	 * 
	 * // JobExecutionDecider Implementation
	 * 
	 * @Bean public Job firstJob() { return
	 * jobBuilderFactory.get("Job1").start(step1()).on("COMPLETED")
	 * .to(decider()).on("TEST_STATUS").to(step2()).from(decider())
	 * .on("*").to(step3()).end().build(); }
	 */

//	// Video - JobExecutionListener Way 1
//	@Bean
//	public JobExecutionListener myJobExecutionListener() {
//		return new MyJobExecutionListener();
//	}

	// Video - JobExecutionListener Way 2
	@Bean
	public MyJobExecutionListener myJobExecutionListener() {
		return new MyJobExecutionListener();
	}

//	// Video - StepExecutionListener Way 1 By implementing the StepExecution interface
//	@Bean
//	public StepExecutionListener myStepExecutionlistener() {
//		return new MyStepExecutionListener();
//	}

	// Video - StepExecutionListener Way 2 By using @BeforeStep and AfterStep
	// Annotations
	@Bean
	public MyStepExecutionListener myStepExecutionlistener() {
		return new MyStepExecutionListener();
	}

	// Sharing Data between Steps using StepExecutionListener
	@Bean
	public StepExecutionListener promotionListener() {
		ExecutionContextPromotionListener promotionListener = new ExecutionContextPromotionListener();
		promotionListener.setKeys(new String[] { "sk1", "sk2" });
		return promotionListener;
	}

	@Bean
	public JobExecutionDecider decider() {
		return new MyJobExecutionDecider();
	}
	

//	// Example of conditional flow part 2
//	@Bean
//	public Job firstJob(JobRepository jobRepository, Step step1, Step step2, Step step3) {
//		return new JobBuilder("job1", jobRepository).start(step1).on("COMPLETED").to(decider()).on("TEST_STATUS")
//				.to(step2).from(decider()).on("*").to(step3).end().build();
//	}

//	// Example of FlowBuilder flow method part 1 - firstJob
//	@Bean
//	public Job job1(JobRepository jobRepository, Step step1, Step step2, Flow flow1) {
//		return new JobBuilder("job1", jobRepository)
//				.start(step1).next(step2)
//				.on("COMPLETED").to(flow1).end().build();
//	}

//	// Sharing Data between Steps
//	@Bean
//	public Job job1(JobRepository jobRepository, Step step1, Step step2, Step step3) {
//		return new JobBuilder("job1", jobRepository).listener(myJobExecutionListener()).start(step1).next(step2)
//				.next(step3).build();
//	}
	
	// JobExecutionDecider Video
	@Bean
	public Job job1(JobRepository jobRepository, Step step1, Step step2, Step step3, Step step4, Step step5) {
		return new JobBuilder("job1", jobRepository).listener(myJobExecutionListener())
				.start(step1).next(step2).next(decider()).on("STEP_3")
				.to(step3).from(decider()).on("STEP_4")
				.to(step4).from(decider()).on("STEP_5")
				.to(step5).end().build();
	}

//	@Bean
//	public Job job2(JobRepository jobRepository, Step job3Step, Flow flow1) {
//		return new JobBuilder("job2", jobRepository).start(flow1).next(job3Step).end().build();
//	}

//	// Parallel Flows using Split - Part 1
//	@Bean
//	public Job job2(JobRepository jobRepository, Flow flow1, Flow flow2) {
//		return new JobBuilder("job2", jobRepository).start(flow1)
//				.split(new SimpleAsyncTaskExecutor()).add(flow2).end().build();
//	}

//	// Parallel Flows using Split - Part 2 (Multiple flows)
//	@Bean
//	public Job job2(JobRepository jobRepository, Flow splitFlow) {
//		return new JobBuilder("job2", jobRepository).start(splitFlow).end().build();
//	}

	// Video - JobExecutionListener Implementation
	@Bean
	public Job job2(JobRepository jobRepository, Flow splitFlow) {
		return new JobBuilder("job2", jobRepository).listener(myJobExecutionListener()).start(splitFlow).end().build();
	}

	// Example - Job3 Nested Jobs
	@Bean
	public Job job3(JobRepository jobRepository, Step step5, Step step6) {
		return new JobBuilder("job3", jobRepository).start(step5).next(step6).build();
	}

	// Example - job3Step
//	@Bean
//	public Step job3Step(JobRepository jobRepository, Step step5, Step step6) {
//		return new JobBuilder("job3Step", jobRepository).start(step5).next(step6).build();
//	}

	// Example - job3Step - job nesting
	@Bean
	public Step job3Step(JobRepository jobRepository, Job job3) {
		return new StepBuilder("job3Step", jobRepository).job(job3).build();
	}

	// Example of FlowBuilder flow method part 1
	@Bean
	public Flow flow1(Step step3, Step step4) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("flow1");
		flowBuilder.start(step3).next(step4).end();
		return flowBuilder.build();

	}

	// Parallel Flows using Split - Part 1
	@Bean
	public Flow flow2(Step step5, Step step6) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("Flow 2");
		flowBuilder.start(step5).next(step6).end();
		return flowBuilder.build();
	}

	@Bean
	public Flow flow3(Step step7, Step step8) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<Flow>("Flow 3");
		flowBuilder.start(step7).next(step8).end();
		return flowBuilder.build();
	}

	// Parallel Flows using Split - Part 2 (Multiple flows)
	@Bean
	public Flow splitFlow(Flow flow1, Flow flow2, Flow flow3) {
		return new FlowBuilder<Flow>("split flow").split(new SimpleAsyncTaskExecutor()).add(flow1, flow2, flow3)
				.build();
	}

	
	// Sharing Data Between Steps using JobExecution and StepExecutionListener
	
	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing Step 1 on Thread............." + Thread.currentThread().getName());
				ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution()
						.getJobExecution().getExecutionContext();
				System.out.println("Job execution context:" + jobExecutionContext);
				ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution()
						.getExecutionContext();
				// jobExecutionContext.put("sk1", "ABC");
				stepExecutionContext.put("sk1", "ABC");
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).listener(promotionListener()).build();
	}

	@Bean
	public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step2", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				boolean isFailure = false;
				if (isFailure) {
					throw new Exception("Test Exception in Step 2");
				}
				System.out.println("Executing Step 2 Thread............." + Thread.currentThread().getName());
				ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution()
						.getJobExecution().getExecutionContext();
				System.out.println("Execution Context:" + jobExecutionContext);
				ExecutionContext stepExecutionContext  = chunkContext.getStepContext().getStepExecution().getExecutionContext();
				//jobExecutionContext.put("sk2", "KLM");
				//stepExecutionContext.put("sk2", "KLM");
				//stepExecutionContext.put("sk2", "TUV");
				stepExecutionContext.put("sk2", "KKK");
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).listener(promotionListener()).build();
	}

	@Bean
	public Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step3", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 3 Thread............." + Thread.currentThread().getName());
				ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution()
						.getJobExecution().getExecutionContext();
				System.out.println("Execution Context:" + jobExecutionContext);
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).listener(myStepExecutionlistener()).build();
	}

	@Bean
	public Step step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step4", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 4 Thread............." + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).build();
	}

	@Bean
	public Step step5(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step5", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				boolean failure = false;
				if (failure) {
					throw new Exception("Test Exception");
				}
				System.out.println("Executing step 5 Thread............." + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).build();
	}

	@Bean
	public Step step6(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step6", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 6 Thread............." + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).build();
	}

	@Bean
	public Step step7(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step7", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 7 Thread............." + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).build();
	}

	@Bean
	public Step step8(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step8", jobRepository).tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Executing step 8 Thread............." + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManager).build();
	}

}
