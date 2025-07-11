package com.tnsif.day12.multithreading;

public class Demo {

	public static void main(String[] args) {
		
		
		Thread myThread=new ThreadLifeCycleDemo();
		
		System.out.println("before Runnable State Thread is alive or not ?"+
		myThread.isAlive());
		
		myThread.start();
		
		try {
			Thread.sleep(450);
			
		}
		catch (InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
		
		System.out.println("After completion execution, it is alive or not ?"
				+myThread.isAlive());

	}

}