package com.tnsif.day4.secondpackage;

import com.tnsif.day4.firstpackage.Base;

public class Executor {

	public static void main(String[] args) {
		
		
		//accessing different package class
		
		Base b1 = new Base();
		
		//private, default , protected and public 
		
		b1.methodPublic();
		b1.varPublic=34567;
		b1.methodPublic();

		//b1.varDefault=354345;
	}

}