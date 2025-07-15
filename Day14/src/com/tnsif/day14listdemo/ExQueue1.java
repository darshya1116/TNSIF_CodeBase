package com.tnsif.day14listdemo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class ExQueue1 {

	public static void main(String[] args) {
		
		Queue<Integer> r=new LinkedList<Integer>();
		
		r.add(6);
		r.add(10);
		r.add(12);
		r.add(13);
		r.add(2);
		
		System.out.println(r);
		System.out.println(r.retainAll(r));
		int positionPeek=r.peek();
		System.out.println("using Peek method first value from queue:"+ positionPeek);
		
		
		int positionelements=r.element();
		System.out.println("Using elemnets method first value from queue"+positionelements);
		
		System.out.println();
		System.out.println(r.remove());
		System.out.println(r);
		
		Iterator<Integer> itr=r.iterator();
		while(itr.hasNext())
		{
			System.out.print(itr.next()+"\t");
		}
		System.out.println();
		
		System.out.println(r.poll());
		System.out.println(r);
		
		System.out.println(r.retainAll(r));
		
		
		
		
		
		
	}
}