package ru.otus;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class HelloOtus {
    public static void main(String... args) {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        int min = 0;
        int max = 10;
        for (int i = min; i < max; i++) {
            multimap.put(String.valueOf(i), "Value "+i);
        }
        System.out.println(multimap);
    }
}
