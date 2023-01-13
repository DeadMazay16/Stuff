package ru.mikheev.kirill.sorting;

import java.util.List;

/**
 * Интерфес дает основу классам сортировок, которые будут оформлены в этом пакете
 * @author Kirill Mikheev
 * @version 1.0
 */

public interface ISort {

    /**
     *
     * Метод принимает и сортирует предоставленный ему массив Comparable объектов
     * @param list массив, который нужно отсортировать
     * @param ascending если true, то сортируется по возрастанию, иначе по убыванию
     * @param <T> Тип элементов массива, обязательно Comparable<T>
     */

    <T extends Comparable<T>> void sort(List<T> list, boolean ascending);
}
