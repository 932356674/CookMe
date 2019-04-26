package com.gss.utils;

import java.util.*;

public class RandomUtils {
    public static Set<Integer> getRondom(int total, int number){
        Set<Integer> list=new HashSet<>();
        Random random=new Random();
        do {
            list.add(random.nextInt(total));
        }while (list.size()!=number);



        return list;
    }

    public static void main(String[] args) {
        Set<Integer> l =RandomUtils.getRondom(3,2);
        for (Integer integer : l) {
            System.out.println(integer);
        }
    }
}
