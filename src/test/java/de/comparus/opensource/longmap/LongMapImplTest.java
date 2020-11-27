package de.comparus.opensource.longmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongMapImplTest {

    LongMap<Integer> integerLongMap;
    LongMap<String> stringLongMap;
    Integer testInteger = 12345789;
    String testString = "Test string";
    Long key = 123L;

    @Before
    public void before() {
        integerLongMap = new LongMapImpl<>();
        stringLongMap = new LongMapImpl<>();
    }

    @Test
    public void putInteger() {
        integerLongMap.put(key, testInteger);
        Assert.assertEquals(testInteger, integerLongMap.get(key));
    }

    @Test
    public void putManyIntegers() {
        Integer integer = 955;
        for (int i = 0; i < 1000; i++) {
            integerLongMap.put(i, i);
        }

        Assert.assertEquals(integer, integerLongMap.get(integer));
    }

    @Test
    public void putString() {
        stringLongMap.put(key, testString);
        Assert.assertEquals(testString, stringLongMap.get(key));
    }

    @Test
    public void testAddMakesIsEmptyFalse() {
        integerLongMap.put(key, testInteger);
        Assert.assertFalse(integerLongMap.isEmpty());
    }

    @Test
    public void getInteger() {
        integerLongMap.put(key, testInteger);
        Integer gottenInt = integerLongMap.get(key);
        Integer gottenIntNull = integerLongMap.get(11111L);
        Assert.assertEquals(testInteger, gottenInt);
        Assert.assertNull(gottenIntNull);
    }

    @Test
    public void getNull() {
        Integer gottenNullInt = integerLongMap.get(11111L);

        Assert.assertNull(gottenNullInt);
    }

    @Test
    public void getString() {
        stringLongMap.put(key, testString);
        String gottenStr = stringLongMap.get(key);
        Assert.assertEquals(testString, gottenStr);
    }

    @Test
    public void remove() {
        integerLongMap.put(key, testInteger);
        integerLongMap.remove(key);
        Assert.assertNull(integerLongMap.get(key));
    }

    @Test
    public void removeValue() {
        integerLongMap.put(key, testInteger);
        Integer integer = integerLongMap.remove(key);
        Assert.assertEquals(testInteger, integer);
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(integerLongMap.isEmpty());
    }

    @Test
    public void isNonEmpty() {
        integerLongMap.put(key, testInteger);
        Assert.assertFalse(integerLongMap.isEmpty());
    }

    @Test
    public void containsKey() {
        integerLongMap.put(key, testInteger);
        Assert.assertTrue(integerLongMap.containsKey(key));
    }

    @Test
    public void nonContainsKey() {
        Assert.assertFalse(integerLongMap.containsKey(key));
    }

    @Test
    public void nonContainsKeyFalse() {
        Assert.assertFalse(integerLongMap.containsKey(key));
    }

    @Test
    public void containsValue() {
        integerLongMap.put(key, testInteger);
        Assert.assertTrue(integerLongMap.containsValue(testInteger));
    }

    @Test
    public void nonContainsValue() {
        Assert.assertFalse(integerLongMap.containsValue(testInteger));
    }

    @Test
    public void keys() {
        integerLongMap.put(key, testInteger);
        long[] keys = integerLongMap.keys();
        long[] keysExpected = {key};
        Assert.assertArrayEquals(keysExpected, keys);
    }

    @Test
    public void emptyKeys() {
        long[] keys = integerLongMap.keys();
        long[] keysEmpty = new long[0];
        Assert.assertArrayEquals(keysEmpty, keys);
    }

    @Test
    public void values() {
        List<Integer> values = new ArrayList<>();
        values.add(testInteger);
        integerLongMap.put(key, testInteger);
        List<Integer> valuesExpected = Arrays.asList(integerLongMap.values());
        Assert.assertEquals(values, valuesExpected);
    }

    @Test
    public void emptyValues() {
        List<Integer> valuesEmpty = Arrays.asList(integerLongMap.values());
        Assert.assertEquals(0, valuesEmpty.size());
    }

    @Test
    public void size() {
        Integer val1 = 1;
        integerLongMap.put(111L, val1);
        Assert.assertEquals(1, integerLongMap.size());
        Integer val2 = 2;
        integerLongMap.put(222L, val2);
        Assert.assertEquals(2, integerLongMap.size());
        Integer val3 = 3;
        integerLongMap.put(333L, val3);
        Assert.assertEquals(3, integerLongMap.size());
    }

    @Test
    public void zeroSize() {
        Assert.assertEquals(0, integerLongMap.size());
    }

    @Test
    public void clear() {
        integerLongMap.put(key, testInteger);
        integerLongMap.clear();
        Assert.assertTrue(integerLongMap.isEmpty());
    }

}