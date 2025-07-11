package com.tnsif.day13.synchronization;

public interface Bank {
	public static final int MINBAl=10000;
    public static final int DAILY_LIMIT=45000;
	public void deposit (int amt) throws DepositLimitException;
	public void withdraw(int amt) throws InsufficientBalanceException;
}
