package com.tnsif.daythree.constructor;

public class Customer {

	private String customerName;
	private int customerId;
	private String customerCity;
	
	
	//default constructor 
	public Customer()
	{
		System.out.println("hello this is Defaul Constructor--------------! ");
	}

	
	

	public Customer(String customerName, int customerId) {
	
		this.customerName = customerName;
		this.customerId = customerId;
		
	}




	//para constructor 
	public Customer(String customerName, int customerId, String customerCity) {
		//this (); //calling default constructor 
		
		System.out.println("Hello is this Para Constructor ");
		this.customerName = customerName;
		this.customerId = customerId;
		this.customerCity = customerCity;
	}


	//getter setter
	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	public String getCustomerCity() {
		return customerCity;
	}


	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}


	//to string
	@Override
	public String toString() {
		return "Customer [customerName=" + customerName + ", customerId=" + customerId + ", customerCity="
				+ customerCity + "]";
	}
	
	
	
	
}