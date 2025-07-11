package com.tnsif.day13.synchronization;

public class SynchronizationDriver {

	public static void main(String[] args) throws InsufficientBalanceException {
		
		
		Account Pritam=new Account(1001, "Pritam", 45000);
		
		System.out.println(Pritam);
		//Pritam.withdraw(7865);
		
		AccountThread t[]=new AccountThread[10];
		for(int i=0;i<10;i++)
		{
			t[i] = new AccountThread(Pritam, 1000 * (i+1));
		}
		try {
			for(int i=0;i<10;i++)
			{
				t[i].join();
			}
		}
		catch(InterruptedException e)
		{
			System.err.println(e.getMessage());
		}
		
		System.out.println("----------------------");
		System.out.println(Pritam);
		

	}

}