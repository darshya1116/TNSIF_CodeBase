package com.tnsif.day14listdemo;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExDequeDemo {

	public static void main(String[] args) {
	
		Deque<String> deque1=new ArrayDeque<String>();
		deque1.add("Tej");
		deque1.add("Pritam");
		deque1.add("Shubham");
		deque1.add("Ishwari");
		
		System.out.println(deque1);
		
		deque1.pollLast();
		System.out.println(deque1);
		
		deque1.pollFirst();
		System.out.println(deque1);
		
		for(String str : deque1)
		{
			System.out.println("queue is :"+ str);
		}
		
		System.out.println(deque1);
		

	}

}