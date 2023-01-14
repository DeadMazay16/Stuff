package ru.mikheev.kirill.sorting;

import java.util.List;

/**
 * Сортировка выбором
 * @author Kirill Mikheev
 * @version 1.0
 */

public class SelectionSort implements ISort {
    /**
     * Метод принимает и сортирует предоставленный ему массив Comparable объектов
     *
     * @param list массив, который нужно отсортировать
     * @param isAscending если true, то сортируется по возрастанию, иначе по убыванию
     * @param <T> Тип элементов массива, обязательно Comparable<T>
     */
    @Override
    public <T extends Comparable<T>> void sort(List<T> list, boolean isAscending) {
        int pivot = 0;
        while (pivot != list.size() - 1) {
            int controlIndex = isAscending ? min(list, pivot) : max(list, pivot);
            T tmp = list.get(controlIndex);
            list.set(controlIndex, list.get(pivot));
            list.set(pivot, tmp);
            pivot++;
        }
    }

    /**
     * Возвращает номер минимального элемента начиная с определенного места массива и до конца
     * @param list массив, котором нужно найти минимум
     * @param start начало зоны поиска (включительно)
     * @param <T> тип элементов массива
     * @return индекс минимального элемента
     */
    private<T extends Comparable<T>> int min(List<T> list, int start) {
        T min = list.get(start);
        int min_index = start;
        for (int i = start + 1; i < list.size(); i++) {
            if(list.get(i).compareTo(min) < 0) {
                min = list.get(i);
                min_index = i;
            }
        }
        return min_index;
    }

    /**
     * Возвращает номер максимального элемента начиная с определенного места массива и до конца
     * @param list массив, котором нужно найти максимум
     * @param start начало зоны поиска (включительно)
     * @param <T> тип элементов массива
     * @return индекс максимального элемента
     */
    private <T extends Comparable<T>> int max(List<T> list, int start) {
        T max = list.get(start);
        int max_index = start;
        for (int i = start + 1; i < list.size(); i++) {
            if(list.get(i).compareTo(max) > 0) {
                max = list.get(i);
                max_index = i;
            }
        }
        return max_index;
    }
}
