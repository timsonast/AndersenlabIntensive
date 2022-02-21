import org.junit.jupiter.api.*;
import ru.timson.MyArrayList;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyArrayListTest {
    MyArrayList<Integer> integers;

    @BeforeAll

    public void  createMyArrayList(){
        integers = new MyArrayList<>();
    }

    @Test
    public void givenMyArrayList_whenAdd_thenAdded(){
        integers.add(5);
        int expected = 1;
        Assertions.assertEquals(integers.size(),expected);
    }

    @Test
    public void givenMyArrayList_whenIsEmpty_thenNullList(){
        Assertions.assertTrue(integers.isEmpty());
    }

    @Test
    public void givenMyArrayList_whenContains_thenTrue(){
        integers.add(5);
        Assertions.assertTrue(integers.contains(5));
    }

    @Test
    public void givenMyArrayList_whenLastIndexOf_thenGetIndex(){
        integers.add(5);
        integers.add(6);
        integers.add(5);
        int expected = 2;
        Assertions.assertEquals(integers.lastIndexOf(5),2);
    }

    @Test
    public void givenMyArrayList_whenGet_thenGetObject(){
        integers.add(5);
        Assertions.assertEquals(integers.get(0),5);
    }

    @Test
    public void givenMyArrayList_whenSet_thenGetObject(){
        integers.add(5);
        integers.add(5);
        Assertions.assertEquals(integers.set(1,3),3);
    }

    @Test
    public void givenMyArrayList_whenAdd_thenAddedInIndex(){
        integers.add(5);
        integers.add(5);
        integers.add(5);
        integers.add(5);
        integers.add(1,3);
        int expected = integers.get(1);
        Assertions.assertEquals(expected,3);
    }

    @Test
    public void givenMyArrayList_whenRemove_thenListsEqualsTrue(){
        MyArrayList<Integer> integers1 = new MyArrayList<>();
        integers1.add(1);
        integers1.add(2);
        integers1.add(3);
        integers1.add(4);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.remove(4);
        Assertions.assertTrue(integers.equals(integers1));
    }

    @Test
    public void givenMyArrayList_whenRemove_thenDeleteObject(){
        MyArrayList<Integer> integers1 = new MyArrayList<>();
        integers1.add(1);
        integers1.add(2);
        integers1.add(3);
        integers1.add(4);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        Integer expected = integers.get(4);
        integers.remove(expected);
        Assertions.assertTrue(integers.equals(integers1));
    }

    @Test
    public void givenMyArrayList_whenClear_thenSizeIsNull(){
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.clear();
        Assertions.assertEquals(integers.size(),0);
    }

    @Test
    public void givenMyArrayList_whenAddAll_thenAddedCollection(){
        Set<Integer> integerSet = new LinkedHashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.addAll(integerSet);
        MyArrayList<Integer> integers1 = (MyArrayList<Integer>) integers.clone();
        Assertions.assertTrue(integers1.equals(integers));
    }

    @Test
    public void givenMyArrayList_whenAdd_thenAddedCollectionInIndex(){
        Set<Integer> integerSet = new LinkedHashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        integers.add(5);
        integers.addAll(1,integerSet);
        MyArrayList<Integer> integers1 = (MyArrayList<Integer>) integers.clone();
        Assertions.assertTrue(integers1.equals(integers));
    }

    @Test
    public void givenMyArrayList_whenRemoveAll_thenSizeIsNull(){
        integers.add(1);
        integers.add(2);
        Set<Integer> integerSet = new LinkedHashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        integers.removeAll(integerSet);
        Assertions.assertEquals(integers.size(),0);
    }

    @Test
    public void givenMyArrayList_whenRetainAll_thenSizeIsNull(){
        integers.add(1);
        integers.add(2);
        Set<Integer> integerSet = new LinkedHashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        integers.retainAll(integerSet);
        Assertions.assertEquals(integers.size(),0);
    }

    @Test
    public void givenMyArrayList_whenContainsAll_thenCollectionEqualsTrue(){
        integers.add(1);
        integers.add(2);
        Set<Integer> integerSet = new LinkedHashSet<>();
        integerSet.add(1);
        integerSet.add(2);
        Assertions.assertTrue(integerSet.containsAll(integerSet));
    }

    @Test
    public void givenMyArrayList_whenToArray_thenArraysEqualsTrue(){
        Integer[] integers1 = {1,2};
        integers.add(1);
        integers.add(2);
        Integer[] integers2 = new Integer[integers1.length];
        integers.toArray(integers2);
        Assertions.assertArrayEquals(integers1,integers2);
    }
}
