package ru.mikheev.kirill;

import ru.mikheev.kirill.sorting.CountSort;

import java.util.concurrent.ThreadLocalRandom;

public class Stuff {
    public static void main(String[] args){
        //System.out.println("PUFF");
//        ClassInformationHolder cih = new ClassInformationHolder(AVLTree.class);
//        cih.getAllInformation();
        int[] list = getList();
        var sort = new CountSort();
        sort.sort(list, 0 ,1000, true);
    }

    public static int[] getList() {
        var result = new int[1_000_000_000];
        for(int i = 0; i < 1_000_000_000; i++) {
            result[i] = ThreadLocalRandom.current().nextInt(0, 100);
        }
        return result;
    }
}
