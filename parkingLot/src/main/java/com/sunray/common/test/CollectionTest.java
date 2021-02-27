package com.sunray.common.test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionTest {

    public static void main(String[] args) throws Exception{
        ArrayList<Object> list = new ArrayList<>();
        list.add(new Object());
        list.add(new Object());
        list.remove(1);

        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add(new Object());
        linkedList.remove(new Object());

        HashSet hashSet = new HashSet();
        hashSet.add(new Object());
        hashSet.iterator();

        TreeSet treeSet = new TreeSet();
        treeSet.add(new Object());
        treeSet.remove(new Object());

        TreeMap treeMap = new TreeMap();
        treeMap.put("Dd", new Object());

        HashMap hashMap = new HashMap();
        hashMap.put("11", new Object());

        Object o = new Object();
        o.hashCode();

        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put("dd", new Object());

    }

}
