package ru.timson;

import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class MyArrayList<E> implements List<E>, RandomAccess, Cloneable{

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private static final Object[] ARRAY_NULL_CAPACITY = {};
    private int size;

    public MyArrayList(int startingCapacity){
        if(startingCapacity > 0){
            this.array = new Object[startingCapacity];
        } else if(startingCapacity == 0){
            this.array = ARRAY_NULL_CAPACITY;
        }else {
            throw new IllegalArgumentException("Лист не может быть такого размера " + startingCapacity);
        }
    }

    public MyArrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(Collection<? extends E> collection){
        Object[] objects = collection.toArray();
        if((size = objects.length) != 0) {
            if(collection.getClass() == MyArrayList.class){
                array = objects;
            } else {
                array = Arrays.copyOf(objects, size, Object[].class);
            }
        } else {
            array = ARRAY_NULL_CAPACITY;
        }
    }

    public void trimToSize() {
        if(size < array.length){
            array = (size == 0) ? ARRAY_NULL_CAPACITY : Arrays.copyOf(array, size);
        }
    }

    public void ensureMinCapacity(int capacity){
        if(size < capacity && DEFAULT_CAPACITY < capacity && Integer.MAX_VALUE - 8 > capacity && capacity > 0) {
            array = Arrays.copyOf(array,capacity);
        }
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    @Override
    public int indexOf(Object object){
        if(object == null){
            for (int i = 0; i <= size ; i++) {
                if(this.array[i] == null){
                    return i;
                }
            }
        } else {
            for (int i = 0; i <= size ; i++) {
                if(object.equals(this.array[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        Object[] objects = array;
        if (object == null) {
            for (int i = 0; i <= size; i--) {
                if (object.equals(objects[i])) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i <= size; i--) {
                if (object.equals(objects[i])) {
                    return i;
                }
            }
        } return -1;
    }

    public Object clone() {
        try {
            MyArrayList<?> myArrayList = (MyArrayList<?>) super.clone();
            myArrayList.array = Arrays.copyOf(array,size);
            return myArrayList;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array,size);
    }

    E object(int index){
        return (E) array[index];
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        return (E) (array[index] = element);
    }

    @Override
    public boolean add(E object) {
        if(size == 0){
            array[size] = object;
            size++;
            return true;
        } else {
            changeOfSize(size);
            array[size++] = object;
            return true;
        }
    }

    @Override
    public void add(int index, E object) {
        rangeCheck(index);
        size++;
        changeOfSize(size);
        System.arraycopy(array, index,array,index + 1, size - index);
        this.array[index] = object;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        E oldObject = (E) array[index];
        if(size > index){
            array[index] = null;
            System.arraycopy(array, index + 1,array,index, size - 1);
            size--;
        }
        return oldObject;
    }

    @Override
    public boolean remove(Object object) {
        if(object == null){
            for (int i = 0; i < size; i++) {
                if(array[i] == null){
                    System.arraycopy(array, i + 1,array,i, size - 1);
                    array[i] = null;
                    size--;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if(array[i].equals(object)){
                    System.arraycopy(array, i + 1,array,i, size - 1);
                    array[i] = null;
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int j = size, i = size = 0; i < j ; i++) {
            array[i] = null;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Object[] objects = collection.toArray();
        if(objects.length == 0){
            return false;
        }
        if(objects.length > 0){
            System.arraycopy(objects,0,array,0,objects.length);
            size = size + objects.length;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        Object[] objects = collection.toArray();
        if(objects.length == 0){
            return false;
        }
        if(objects.length > 0){
            size = size + objects.length;
            changeOfSize(size);
            System.arraycopy(array,index-1,array,index + objects.length,size - objects.length);
            size = array.length;
            System.arraycopy(objects,0,array,index-1,objects.length);
        }
        return true;
    }


    @Override
    public boolean removeAll(Collection<?> collection) {
        return batchRemove(collection, true, 0, size);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return batchRemove(collection, true, 0, size);
    }

    boolean batchRemove(Collection<?> c, boolean complement,
                        final int from, final int end) {
        Objects.requireNonNull(c);
        final Object[] es = array;
        int r;
        for (r = from;; r++) {
            if (r == end)
                return false;
            if (c.contains(es[r]) != complement)
                break;
        }
        int w = r++;
        try {
            for (Object e; r < end; r++)
                if (c.contains(e = es[r]) == complement)
                    es[w++] = e;
        } catch (Throwable ex) {
            System.arraycopy(es, r, es, w, end - r);
            w += end - r;
            throw ex;
        } finally {
            shiftTailOverGap(es, w, end);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Objects.requireNonNull(collection);
        Object[] a = collection.toArray();
        if(array.equals(a)){
            return true;
        }
        return false;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(array, size, a.getClass());
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        rangeCheck(fromIndex);
        rangeCheck(toIndex);
        E[] a = (E[]) new Object[toIndex-fromIndex];
        System.arraycopy(array,fromIndex,a,0,toIndex-fromIndex);
        return new ArrayList<E>(List.of(a));
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        rangeCheck(index);
        return new ListItr(index);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof List)) {
            return false;
        }

        boolean equal = (o.getClass() == MyArrayList.class)
                ? equalsArrayList((MyArrayList<?>) o)
                : equalsRange((List<?>) o, 0, size);

        return equal;
    }

    boolean equalsRange(List<?> other, int from, int to) {
        final Object[] es = array;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        var oit = other.iterator();
        for (; from < to; from++) {
            if (!oit.hasNext() || !Objects.equals(es[from], oit.next())) {
                return false;
            }
        }
        return !oit.hasNext();
    }

    private boolean equalsArrayList(MyArrayList<?> other) {
        final int s = size;
        boolean equal;
        if (equal = (s == other.size)) {
            final Object[] otherEs = other.array;
            final Object[] es = array;
            if (s > es.length || s > otherEs.length) {
                throw new ConcurrentModificationException();
            }
            for (int i = 0; i < s; i++) {
                if (!Objects.equals(es[i], otherEs[i])) {
                    equal = false;
                    break;
                }
            }
        }
        return equal;
    }

    public int hashCode() {
        int hash = hashCodeRange(0, size);
        return hash;
    }

    int hashCodeRange(int from, int to) {
        final Object[] es = array;
        if (to > es.length) {
            throw new ConcurrentModificationException();
        }
        int hashCode = 1;
        for (int i = from; i < to; i++) {
            Object e = es[i];
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    private void rangeCheck(int index){
        if(size <= index){
            throw new IndexOutOfBoundsException("Неверный размер листа");
        }
    }

    private void changeOfSize(int size){
        if (size >= DEFAULT_CAPACITY) {
            Arrays.copyOf(array,size + 10);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(array[i] + " ");
        }
        return sb.toString();
    }

    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, size - hi);
        for (int to = size, i = (size -= hi - lo); i < to; i++)
            es[i] = null;
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;


        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] objects = MyArrayList.this.array;
            if (i >= array.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) objects[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int size = MyArrayList.this.size;
            int i = cursor;
            if (i < size) {
                final Object[] es = array;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < size; i++)
                    action.accept(elementAt(es, i));
                cursor = i;
                lastRet = i - 1;
            }
        }

    }

    private class ListItr extends Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = MyArrayList.this.array;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastRet = i];
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {

            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
