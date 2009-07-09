/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.smgame.util;

public class testArray {

    public static void main(String args[]) {
        String a, b, c;

        //     a = new String("test");
        a = "test";
        b = a;

        System.out.println("a==b " + (a == b));

        b = "test";
//      b = new String("test");


        System.out.println("a==b " + (a == b));

        System.out.println("a.equals(b) " + a.equals(b));
    }
}
