package ru.mikheev.kirill.distributed.hash;

import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        HashDistributor hashDistributor = new HashDistributor(List.of("first", "second", "third", "forth"), 11);
        hashDistributor.print();
        String str = "a";
        for (int i = 0; i < 10; i++) {
            System.out.println(str.hashCode());
            System.out.println(hashDistributor.getServer(str.hashCode()));
            str += "a";
        }
    }
}
