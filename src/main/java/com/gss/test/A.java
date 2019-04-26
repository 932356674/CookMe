package com.gss.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class A {

   /* public static void main(String[]args){
        Md5Hash md5Hash = new Md5Hash("admin","18888888888",1024);
        System.out.println(md5Hash.toString());

         md5Hash = new Md5Hash("string","33333333333",1024);
         md5Hash = new Md5Hash("string","22222222222",1024);
        System.out.println(md5Hash.toString());
    }*/
   private String a=functionB("print functionB In A");
   private static String b=functionA();
   public A(){
      System.out.println("print context A");
   }
   public static String functionA(){
      System.out.println("pint functionA in. A");
      return "";
   }
   public String functionB(String s){
      System.out.println(s);
      return s;
   }

}
