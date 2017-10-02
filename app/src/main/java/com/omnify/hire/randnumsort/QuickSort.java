package com.omnify.hire.randnumsort;

import java.util.ArrayList;

public class QuickSort {
    public void sort(ArrayList<Integer> a) {
        quickSort(a, 0, a.size() - 1);
    }

    private void quickSort(ArrayList<Integer> a, int low, int high) {
        int i = low;
        int j = high;
        Integer pivot = a.get(low + (high - low)/2);

        while (i <= j) {
            while (a.get(i) < pivot) {
                i++;
            }
            while (a.get(j) > pivot) {
                j--;
            }
            if (i <= j) {
                swap(a, i, j);
                i++;
                j--;
            }
        }
        
        if (low < j)
            quickSort(a, low, j);
        if (i < high)
            quickSort(a, i, high);
    }

    private void swap(ArrayList<Integer> a, int i, int j) {
        Integer temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }
}