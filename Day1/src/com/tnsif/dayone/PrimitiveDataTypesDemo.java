package com.tnsif.dayone;

public class PrimitiveDataTypesDemo {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		//byte takes 1 byte
		//1 byte = 8 bits     // 2^16
		//256/2  
		//128
		
		byte byteMax=127;
		byte byteMin=-128;	
		
		System.out.println("Min range of byte is : "+ byteMin +
				"  Max range of byte is "+ byteMax);
		
		//short ---- 2 byte 
		short shortMax=  32767;
		short shortMin=	-32768;	
		
		System.out.println("Min range of short is : "+ shortMin +
				"  Max range of short is "+ shortMax);

		//int --- 4 byte
		int maxInt=2147483647;
		int minInt=-2147483648;	
		
		//long -- 8 byte
		long maxLong=9223372036854775807L;
		
		long minLong=-9223372036854775808L;	
		
		float f=3234.141243278345f;
		double d=3456.14124512345678902345678914f;
		
		boolean flag=false;
		boolean flag2=true;
		
	}

}