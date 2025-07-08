package com.tnsif.day2.operationdemo;

public class Operations {

    public static void main(String[] args) {

        int a = 10;
        int b = 20;
        System.out.println("A and B value before the operator: " + a + " " + b);

        ++a;

        int c1 = ++a + b + a--;
        System.out.println("C value after the operator: " + c1);

        int d = c1++ + a + b--;
        System.out.println("D value after the operation: " + d);
    }
}
