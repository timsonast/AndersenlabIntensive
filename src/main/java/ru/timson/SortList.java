package ru.timson;

import java.util.Comparator;
import java.util.Objects;

public class SortList {

    public static <T extends Comparable<? super T>> void quickSort(MyArrayList<T> myArrayList) {

        sort(myArrayList.toArray(),0,myArrayList.toArray().length-1);
    }

    private static void sort(Object[] objects, int low, int high){
        Comparator<Object> comparator = new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable<Object>)o1).compareTo(o2);
            }
        };
        if (objects.length == 0)
            return;//завершить выполнение если длина массива равна 0

        if (low >= high)
            return;//завершить выполнение если уже нечего делить

        // выбрать опорный элемент
        int middle = low + (high - low) / 2;
        Object opora = objects[middle];

        // разделить на подмассивы, который больше и меньше опорного элемента
        int i = low, j = high;

        while (i <= j) {
            int k = comparator.compare(objects[low],objects[middle]);
            while (k < 0) {
                k++;
                i++;
            }

            while (k > 0) {
                k--;
                j--;
            }

            if (i <= j) {//меняем местами
                Object temp = objects[i];
                objects[i] = objects[j];
                objects[j] = temp;
                i++;
                j--;
            }
        }

        // вызов рекурсии для сортировки левой и правой части
        if (low < j)
            sort(objects, low, j);

        if (high > i)
            sort(objects, i, high);
    }
}
