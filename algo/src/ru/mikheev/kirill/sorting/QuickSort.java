package ru.mikheev.kirill.sorting;

import java.util.Comparator;
import java.util.List;

public class QuickSort implements ISort {

    @Override
    public <T extends Comparable<T>> void sort(List<T> list, boolean isAscending) {
        if(list.size() == 0) return;
        partSort(list, 0, list.size() - 1, isAscending ? Comparable::compareTo : Comparator.reverseOrder());
    }

    private <T extends Comparable<T>> void partSort(List<T> list, int begin, int end, Comparator<T> comparator) {
        if(begin >= end) return;
        int left_pivot = begin, right_pivot = end;
        T middle = list.get((right_pivot - left_pivot) / 2 + left_pivot);
        while (left_pivot <= right_pivot) {
            while(comparator.compare(list.get(left_pivot), middle) < 0) left_pivot++;
            while(comparator.compare(list.get(right_pivot), middle) > 0) right_pivot--;
            if(left_pivot <= right_pivot) {
                T tmp = list.get(left_pivot);
                list.set(left_pivot, list.get(right_pivot));
                list.set(right_pivot, tmp);
                left_pivot++;
                right_pivot--;
            }
        }
        if(begin < right_pivot)
            partSort(list, begin, right_pivot, comparator);
        if(end > left_pivot)
            partSort(list, left_pivot, end, comparator);
    }
}
