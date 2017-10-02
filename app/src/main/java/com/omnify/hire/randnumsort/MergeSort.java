package com.omnify.hire.randnumsort;

import java.util.ArrayList;

public class MergeSort {
    public static void sort(ArrayList<Integer> a) {
        ArrayList<Integer> tmp = new ArrayList<>(a.size());
        for (int i = 0; i < a.size(); i++)
            tmp.add(0);

        sort(a, tmp, 0, a.size() - 1);
    }

    private static void sort(ArrayList<Integer> a, ArrayList<Integer> tmp, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            sort(a, tmp, left, center);
            sort(a, tmp, center + 1, right);
            merge(a, tmp, left, center + 1, right);
        }
    }

    private static void merge(ArrayList<Integer> a, ArrayList<Integer> tmp,
                              int left, int right, int rightEnd) {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if (a.get(left).compareTo(a.get(right)) <= 0)
                tmp.set(k++, a.get(left++));
            else
                tmp.set(k++, a.get(right++));

        while(left <= leftEnd)
            tmp.set(k++, a.get(left++));

        while(right <= rightEnd)
            tmp.set(k++, a.get(right++));

        for(int i = 0; i < num; i++, rightEnd--)
            a.set(rightEnd, tmp.get(rightEnd));
    }
}
