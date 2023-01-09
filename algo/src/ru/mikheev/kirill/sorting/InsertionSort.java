package ru.mikheev.kirill.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Сортировка вставками
 * @author Kirill Mikheev
 * @version 1.0
 */

public class InsertionSort implements ISort {
    /**
     * Метод принимает сортирует предоставленный ему массив Comparable объектов
     *
     * @param list      массив, который нужно отсортировать
     * @param ascending если true, то сортируется по возрастанию, иначе по убыванию
     */
    @Override
    public <T extends Comparable<T>> void sort(List<T> list, boolean ascending) {
        int pivot = 1;
        Comparator<T> comparator = (first, second) -> ascending ? first.compareTo(second) : second.compareTo(first);
        while(pivot != list.size()) {
            int second_pivot = pivot - 1;
            while(second_pivot >= 0 && comparator.compare(list.get(pivot), list.get(second_pivot)) < 0) {
                second_pivot--;
            }
            if(second_pivot + 1 != pivot) {
                T tmp = list.remove(pivot);
                list.add(second_pivot + 1, tmp);
            }
            pivot++;
        }
    }
}
