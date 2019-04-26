package com.gss.test;

public class B extends A {
    private String a=functionB("print functionB In B");
    private static String b=functionA();
    public B(){
        System.out.println("print construct B");
    }
    public static String functionA(){
        System.out.println("print functionA in B");
        return "";
    }
    public String functionB(String s){
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        A a=new B();
    }
}
