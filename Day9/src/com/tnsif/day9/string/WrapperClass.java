package com.tnsif.day9.string;

public class WrapperClass {

	@SuppressWarnings("removal")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//unboxing
		Integer i=new Integer (10);
		System.out.println(i);
		int b=i.intValue();
		System.out.println(b);
		
		//without using int value()
		int c=i;
		System.out.println(c);
		
		//Autoboxing
		int a=100;
		Integer i1=a;
		System.out.println(i1);
	}

}