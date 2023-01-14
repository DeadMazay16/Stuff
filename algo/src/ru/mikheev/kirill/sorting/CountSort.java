package ru.mikheev.kirill.sorting;

public class CountSort {

    public void sort(int[] array, int leftSide, int rightSide, boolean ascending) {
        int[] counter = new int[rightSide - leftSide];
        for(var elem : array) {
            counter[elem - leftSide]++;
        }
        int currFillIndex = 0;
        if(ascending) {
            for (int i = 0; i < counter.length; i++) {
                for (int j = 0; j < counter[i]; j++) {
                    array[currFillIndex++] = i + leftSide;
                }
            }
        }else{
            for (int i = counter.length - 1; i >= 0; i--) {
                for (int j = 0; j < counter[i]; j++) {
                    array[currFillIndex++] = i + leftSide;
                }
            }
        }
    }
}
