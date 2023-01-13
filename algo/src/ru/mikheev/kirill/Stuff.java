package ru.mikheev.kirill;

import ru.mikheev.kirill.sorting.ISort;
import ru.mikheev.kirill.sorting.ParallelQuickSort;
import ru.mikheev.kirill.sorting.QuickSort;
import ru.mikheev.kirill.structures.AVLTree;
import ru.mikheev.kirill.utilities.ClassInformationHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Stuff {
    public static void main(String[] args){
        //System.out.println("PUFF");
//        ClassInformationHolder cih = new ClassInformationHolder(AVLTree.class);
//        cih.getAllInformation();

        List<Integer> list = getList();
        ISort sort = new ParallelQuickSort();
        sort.sort(list, true);
    }

    public static List<Integer> getList() {
        var result = new ArrayList<Integer>(100_000_000);
        for(int i = 0; i < 100_000_000; i++) {
            result.add(ThreadLocalRandom.current().nextInt(0, 10000));
        }
        return result;
    }
}
