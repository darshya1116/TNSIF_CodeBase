package com.tnsif.day10.singledimension;

import java.util.Arrays;

public class ArrayClassDemo {

	public static void main(String[] args) {
		
		//get the array 
		int intArr[] = {69,20,12,40,35};

		//print the array 
		
		System.out.println(Arrays.toString(intArr));

		for(int i=0; i<intArr.length;i++)
		{
			System.out.print(intArr[i]+ " ");
		}
	
		System.out.println();

		//sort 
		Arrays.sort(intArr);
		System.out.println(Arrays.toString(intArr));
		
		//binary search
		System.out.println(Arrays.binarySearch(intArr, 15));
		
		
		//comparison
		System.out.println(Arrays.toString(intArr));
		
		//get the second array
		int intArr1[] = {69,20,12,40,35};
		System.out.println(Arrays.toString(intArr1));
		
		//Arrays.sort(intArr1);
		
		//comparison both the array
		if(Arrays.compare(intArr, intArr1)==0)
		{
			System.out.println("Both the arrays are same ");
		}
		else
		{
			System.out.println("Both arrays are not same ");
		}
		
		//copy array
		
		int intArr2[] = Arrays.copyOf(intArr1, 2);
		System.out.println(Arrays.toString(intArr2));
		
		int intArr3[] = Arrays.copyOfRange(intArr1, 2, 4);
		System.out.println(Arrays.toString(intArr3));
		
		//fill method 
		Arrays.fill(intArr3, 7);
		System.out.println(Arrays.toString(intArr3));
	}

}