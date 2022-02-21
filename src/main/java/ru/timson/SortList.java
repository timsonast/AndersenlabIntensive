package ru.timson;

import java.util.*;

public class SortList<E> {

    public static <E> List<E> quickSort(List<E> array, Comparator<E> sort, int  start, int end){


        if(start>=0 && start<=array.size() && end>=0 && end<=array.size()){
            E middleElement = array.get((start+end)/2);
            int i = start, j = end;

            while (i <= j) {

                while (sort.compare(array.get(i),middleElement) < 0) {
                    i++;
                }
                while (sort.compare(array.get(j),middleElement) > 0) {
                    j--;
                }

                if (i <= j) {
                    E temp = array.get(i);
                    array.set(i,array.get(j));
                    array.set(j,temp);
                    i++;
                    j--;
                }
            }

            if (start < j)
                quickSort(array,sort, start, j);
            if (end > i)
                quickSort(array,sort, i, end);

            return array;
        }else{
            throw new NoSuchElementException();
        }
    }
}
