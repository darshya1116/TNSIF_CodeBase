package com.tnsif.dayeight.abstractionclassdemo;

public class ShapeDemo {

	public static void main(String[] args) {
		
		
		Shape s;
		
		s=new Square();
		s.calArea();
		s.show();
		
		s=new Rectangular();
		s.calArea();
		s.show();
		
		s=new Square(25.89f);
		s.calArea();
		s.show();
		
		s=new Rectangular(12, 11);
		s.calArea();
		s.show();

	}

}