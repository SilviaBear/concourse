package org.cinchapi.concourse.util;

import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

import com.google.common.collect.Iterables;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TCollectionsLargerBetweenTest {
    private Collection<?> a;
    private Collection<?> b;
    private Object expected;
    
    public TCollectionsSmallerBetweenTest(Collection<?> a, Collection<?> b, Object expected) {
	this.a = a;
	this.b = b;
	this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection input() {
	ArrayList<Integer> a1 = new ArrayList<Integer>();
	a1.add(1);
	a1.add(2);
	ArrayList<Integer> a2 = new ArrayList<Integer>();
	a2.add(3);
	a2.add(4);
	ArrayList<Integer> a3 = new ArrayList<Integer>();
	a3.add(1);
	return Arrays.asList(new Object[][] {
		{null, null, null},
		{null, new ArrayList(), null},
		{new ArrayList(), null, new ArrayList()},
		{new ArrayList(), new ArrayList(), new ArrayList()},
		{a1, a2, a1},
		{a3, a1, a1},
		{a1, a3, a1}
	    });
    }
    
    @Test
    public void testLargerBetween() {
	assertEquals(expected, TCollections.largerBetween(a, b));
    }
}
