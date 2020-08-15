package org.itstep.Task1;

public class Test {
    @RunIt(a = 1, b = 5)
    public static void test(int a, int b){
        System.out.println("a = " + a + "; b = " + b + " sum = " + (a + b));
    }
}
