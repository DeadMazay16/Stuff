package ru.mikheev.kirill.sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort implements ISort {


    @Override
    public <T extends Comparable<T>> void sort(List<T> list, boolean ascending) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        var sortAction = new PartSort<>(list, 0, list.size() - 1, ascending ? Comparable::compareTo : (first, second) -> second.compareTo(first));
        forkJoinPool.execute(sortAction);
        sortAction.join();

    }

    private class PartSort<T extends Comparable<T>> extends RecursiveAction {
        private List<T> list;
        private int begin, end;
        private Comparator<T> comparator;

        public PartSort(List<T> list, int begin, int end, Comparator<T> comparator) {
            this.list = list;
            this.begin = begin;
            this.end = end;
            this.comparator = comparator;
        }


        @Override
        protected void compute() {
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
            List<PartSort> tasks = new ArrayList<>();
            if(begin < right_pivot)
                tasks.add(new PartSort(list, begin, right_pivot, comparator));
            if(end > left_pivot)
                tasks.add(new PartSort(list, left_pivot, end, comparator));
            ForkJoinTask.invokeAll(tasks);
        }
    }
}
