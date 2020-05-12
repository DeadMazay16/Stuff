package ru.mikheev.kirill.structures;


import java.util.ArrayList;
import java.util.List;

/**
 * Структура данных куча
 * @author Kirill Mikheev
 * @version 1.0
 */

public class Heap <T extends Comparable<T>> {

    /** Список, в котором хранятся элементы кучи */
    private List<T> list;

    /**
     * Пустой конструктор инициализации кучи
     */
    public Heap() {
        list = new ArrayList<>();
    }

    /**
     * Конструктор инициализации кучи на основе списка
     * @param list список, из которого будут скопированы элементы для создания кучи
     */
    public Heap(List<T> list) {
        this.list = new ArrayList<>(list);
        for (int i = getHeapSize() / 2; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Добавление нового элемента в кучу
     * @param element
     */
    public void add(T element) {
        list.add(element);
        int i = getHeapSize() - 1;
        int parent = (i - 1) / 2;
        while (i > 0 && list.get(parent).compareTo(list.get(i)) < 0) {
            T tmp = list.get(i);
            list.set(i, list.get(parent));
            list.set(parent, tmp);
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    /**
     * Изъятие макимального элемента из кучи без нарушения ее основного свойства
     * @return maxElement
     */
    public T getMax() {
        T result = list.get(0);
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        heapify(0);
        return result;
    }

    /**
     * @return размер кучи
     */
    public int getHeapSize() {
        return list.size();
    }

    /**
     * Усадка кучи относительно определенного элемента
     * @param i элемент, относительно которого происходит усадка
     */
    private void heapify(int i) {
        int leftChild;
        int rightChild;
        int largestChild;

        while(true) {
            leftChild = 2 * i + 1;
            rightChild = 2 * i + 2;
            largestChild = i;
            if (leftChild < this.getHeapSize() && list.get(leftChild).compareTo(list.get(largestChild)) > 0) {
                largestChild = leftChild;
            }
            if (rightChild < this.getHeapSize() && list.get(rightChild).compareTo( list.get(largestChild)) > 0) {
                largestChild = rightChild;
            }
            if (largestChild == i) {
                break;
            }
            T tmp = list.get(i);
            list.set(i, list.get(largestChild));
            list.set(largestChild, tmp);
            i = largestChild;
        }
    }
}
