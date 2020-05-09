/*************************************************
 * Program: Complextest
 *
 * Author: Thomas Conrad (s183742)
 *
 * Course: 02102 Indledende Programmering F12
 *
 * Purpose: To test the Complex class
 *
 * Usage: Generates two Complex objects, and
 * tests some methods.
 * ***********************************************/
public class ComplexTest {

    public static void main(String[] args) {
        Complex z1 = new Complex(1,2);
        Complex z2 = new Complex(4,5);
        System.out.println(z1.plus(z2));
        System.out.println(z1.times(z2));
        System.out.println(z1.divide(z2));
        System.out.println(z1.norm());

    }

}