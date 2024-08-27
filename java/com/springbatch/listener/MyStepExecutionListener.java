package com.springbatch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

public class MyStepExecutionListener {
//implements StepExecutionListener {

//	@Override
//	public void beforeStep(StepExecution stepExecution) {
//		// TODO Auto-generated method stub
//		System.out.println("STEP NAME:" + stepExecution.getStepName());
//		System.out.println("STEP EXIT STATUS: "+ stepExecution.getExitStatus());
//		System.out.println("STEP START TIME: "+ stepExecution.getStartTime());
//		System.out.println(stepExecution.getStepName() + "EXECUTED ON THREAD " + Thread.currentThread().getName());
//
//	}
//
//	@Override
//	public ExitStatus afterStep(StepExecution stepExecution) {
//		// TODO Auto-generated method stub
//		//return new ExitStatus("TEST_STATUS");
//		System.out.println("STEP NAME:" + stepExecution.getStepName());
//		System.out.println("STEP EXIT STATUS: "+ stepExecution.getExitStatus());
//		System.out.println("STEP END TIME: "+ stepExecution.getEndTime());
//		System.out.println(stepExecution.getStepName() + "EXECUTED ON THREAD " + Thread.currentThread().getName());
//		return null;
//	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		System.out.println("STEP NAME:" + stepExecution.getStepName());
		System.out.println("STEP EXIT STATUS: "+ stepExecution.getExitStatus());
		System.out.println("STEP START TIME: "+ stepExecution.getStartTime());
		System.out.println(stepExecution.getStepName() + "EXECUTED ON THREAD " + Thread.currentThread().getName());

	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		//return new ExitStatus("TEST_STATUS");
		System.out.println("STEP NAME:" + stepExecution.getStepName());
		System.out.println("STEP EXIT STATUS: "+ stepExecution.getExitStatus());
		System.out.println("STEP END TIME: "+ stepExecution.getEndTime());
		System.out.println(stepExecution.getStepName() + "EXECUTED ON THREAD " + Thread.currentThread().getName());
		return null;
	}

}
