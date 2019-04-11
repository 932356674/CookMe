package com.gss.test;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestMD5 {

    public static void main(String[]args){
        Md5Hash md5Hash = new Md5Hash("admin","18888888888",1024);
        System.out.println(md5Hash.toString());

         md5Hash = new Md5Hash("string","33333333333",1024);
         md5Hash = new Md5Hash("string","22222222222",1024);
        System.out.println(md5Hash.toString());
    }
}
