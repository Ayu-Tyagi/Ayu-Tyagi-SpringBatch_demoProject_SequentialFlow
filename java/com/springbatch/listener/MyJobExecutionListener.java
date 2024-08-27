package com.springbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.item.ExecutionContext;

public class MyJobExecutionListener {
// implements JobExecutionListener {
	
//	@Override
//	public void beforeJob(JobExecution jobExecution) {
//		System.out.println("JOB NAME: " + jobExecution.getJobInstance().getJobName());
//		System.out.println("JOB PARAMETERS: "+ jobExecution.getJobParameters());
//		System.out.println("JOB START TIME: " + jobExecution.getStartTime());
//	}
//	
//	@Override
//	public void afterJob(JobExecution jobExecution) {
//		System.out.println("JOB NAME: " + jobExecution.getJobInstance().getJobName());
//		System.out.println("JOB PARAMETERS: "+ jobExecution.getJobParameters());
//		System.out.println("JOB END TIME: " + jobExecution.getEndTime());
//	}
	
	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		System.out.println("JOB NAME: " + jobExecution.getJobInstance().getJobName());
		System.out.println("JOB PARAMETERS: "+ jobExecution.getJobParameters());
		System.out.println("JOB START TIME: " + jobExecution.getStartTime());
		ExecutionContext jobExecutionContext = jobExecution.getExecutionContext();
		jobExecutionContext.put("jk1", "XYZ");
	}
	
	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		System.out.println("JOB NAME: " + jobExecution.getJobInstance().getJobName());
		System.out.println("JOB PARAMETERS: "+ jobExecution.getJobParameters());
		System.out.println("JOB END TIME: " + jobExecution.getEndTime());
	}

}
