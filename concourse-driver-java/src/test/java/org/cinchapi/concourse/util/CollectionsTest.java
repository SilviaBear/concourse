package org.cinchapi.concourse.util;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import static org.junit.Assert.*;

public class CollectionsTest {

    @Test
    public void testNullWithToList() {
	assertNull(Collections.toList(null));
    }

    @Test
    public void testEmptyArrayListWithToList() {
	List<Integer> input = new ArrayList<Integer>();
	assertEquals(new ArrayList<Integer>(), Collections.toList(input));
    }

    @Test
    public void testEmptySetWithToList() {
	Set<Integer> input = new HashSet<Integer>();
	assertEquals(new ArrayList<Integer>(), Collections.toList(input));
    }

    @Test
    public void testArrayListWithToList() {
	List<Integer> expected = new ArrayList<Integer>();
	expected.add(1);
	assertEquals(expected, Collections.toList(expected));
    }

    @Test
    public void testSetWithToList() {
	Set<Integer> input = new HashSet<Integer>();
	input.add(1);
	List<Integer> expected = new ArrayList<Integer>();
	expected.add(1);
	assertEquals(expected, Collections.toList(input));
    }
    
    @Test
    public void testArrayListWithToLongList() {
	List<Integer> input = new ArrayList<Integer>();
	input.add(1);
	input.add(2);
	List<Long> expected = new ArrayList<Long>();
	expected.add(new Long(1));
	expected.add(new Long(2));
	assertEquals(expected, Collections.toLongList(input));
    }

    @Test
    public void testNullWithToLongList() {
	assertNull(Collections.toLongList(null));
    }
    @Test
    public void testEmptyArrayWithToLongList() {
	List<Integer> input = new ArrayList<Integer>();
	assertEquals(new ArrayList<Long>(), Collections.toLongList(input));
    }

    @Test
    public void testEmptySetWithToLongList() {
	Set<Integer> expected = new HashSet<Integer>();
	assertEquals(new ArrayList<Long>(), Collections.toLongList(expected));
    }

    @Test
    public void testSetWithToLongList() {
	Set<Integer> input = new HashSet<Integer>();
	input.add(1);
	List<Long> expected = new ArrayList<Long>();
	expected.add(new Long(1));
	assertEquals(expected, Collections.toLongList(input));
    }

    @Test
    public void testLongArrayListWithToLongList() {
	List<Long> input = new ArrayList<Long>();
	input.add(new Long(1));
	assertEquals(input, Collections.toLongList(input));
    }

    @Test
    public void testEmptyLongArrayWithToLongList() {
	List<Long> input = new ArrayList<Long>();
	assertEquals(input, Collections.toLongList(input));
    }

    @Test
    public void testLongSetWithToLongList() {
	Set<Long> input = new HashSet<Long>();
	input.add(new Long(1));
	List<Long> expected = new ArrayList<Long>();
	expected.add(new Long(1));
	assertEquals(expected, Collections.toLongList(input));
    }

    @Test
    public void testEmptyLongSetWithToLongList() {
	Set<Long> input = new HashSet<Long>();
	List<Long> expected = new ArrayList<Long>();
	System.out.println(Collections.toLongList(input));
	assertEquals(expected, Collections.toLongList(input));
    }

    @Test(expected = ClassCastException.class)
    public void testInvalidType() {
	List<A> input = new ArrayList<A>();
	input.add(new A());
	Collections.toLongList(input);
    }
}

class A {
}

