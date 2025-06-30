package project;

public class PrimitiveDataTypesDemo {
	public static void main(String[] args) {
		//byte taken 1 byte 
		//1 byte = 8 bits    //256
		//256/2
		//128
		
		
		byte byteMax= 127;
		byte byteMin= -128;		
		
		System.out.println("Min range of byte is :" + byteMin + "Max range of byte is"+ byteMax);
		
		//short -----2 byte 
		short shortMax = 32767;
		short shortMin = -32768;
		
		System.out.println("Min range of short is :" + shortMin + "Max range of short is"+ shortMax);
		
		//int --4bytes
		
		int maxInt = 2147483647;
		int minInt = -2147483648;
		
		//long --- 8 bytes
		
		long maxLong = 922337203685477507L;
		long minLong = -9223372036854775808L;
		
		float f = 3234.55426242f;
		double d = 3456.1426246326d;
		
		boolean flag1 = false;
		boolean flag2 = true;
		

	}

}
