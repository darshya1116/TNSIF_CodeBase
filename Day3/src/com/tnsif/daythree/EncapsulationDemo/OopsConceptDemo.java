package com.tnsif.daythree.EncapsulationDemo;

public class OopsConceptDemo {

	private int serailNum;
	private String name;
	private int age;
	
	//getter and setter 
	public int getSerailNum() {
		return serailNum;
	}
	public void setSerailNum(int serailNum) {
		this.serailNum = serailNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	//to String 
	@Override
	public String toString() {
		return "OopsConceptDemo [serailNum=" + serailNum + ", name=" + name + ", age=" + age + "]";
	}
	
	
	
	
	
	
}