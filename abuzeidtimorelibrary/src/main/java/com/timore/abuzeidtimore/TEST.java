package com.timore.abuzeidtimore;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by ZEID on 6/7/2015.
 */
public class TEST {
    public static void printMap(Map map){
        System.err.println("@@@@@@@@@@@@@@@@@@@@@@@  PRINT HASHMAP @@@@@@@@@@@@@@@@@@@@@@@@@@");
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            Object temp = iterator.next();
            System.err.println("KEY::"+temp.toString()+"#VALUE# "+map.get(temp));
        }


    }
}
