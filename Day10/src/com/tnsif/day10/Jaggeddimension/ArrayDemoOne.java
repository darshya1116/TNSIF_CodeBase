package com.tnsif.day10.Jaggeddimension;

class ArrayOperations {

    // Print int array
    static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + "\t");
        }
        System.out.println();
    }

    // Variable argument function
    public static int getSum(int... n) {
        int sum = 0;
        for (int no : n)
            sum += no;
        return sum;
    }

    // Count number of odd elements
    public static int getOddCount(int[] b) {
        int count = 0;
        for (int i = 0; i < b.length; i++) {
            if (b[i] % 2 != 0)
                count++;
        }
        return count;
    }

    // Count number of even elements
    public static int getEvenCount(int[] b) {
        return b.length - getOddCount(b);
    }
}

public class ArrayDemoOne {

    public static void main(String[] args) {

        int n = 10;
        int a[] = new int[n]; // declaration + instantiation

        // Assign values and display
        for (int i = 0; i < a.length; i++) {
            a[i] = 5 * i;
        }

        System.out.println("Array a:");
        ArrayOperations.printArray(a);

        int b[] = { 10, 20, 30, 40, 50 };
        System.out.println("Array b:");
        ArrayOperations.printArray(b);

        // Call variable argument function
        System.out.println("Sum of array elements is: " + ArrayOperations.getSum(b));
        System.out.println("Sum of values is: " + ArrayOperations.getSum(10, 20, 30, 40, 60, 80));

        // Modify and reprint
        b[2] = 999;
        System.out.println("Modified Array b:");
        ArrayOperations.printArray(b);

        System.out.println("Odd numbers: " + ArrayOperations.getOddCount(b) +
                "\tEven numbers: " + ArrayOperations.getEvenCount(b));
    }
}
