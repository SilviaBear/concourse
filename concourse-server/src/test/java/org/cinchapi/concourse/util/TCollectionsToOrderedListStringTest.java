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
public class TCollectionsToOrderedListStringTest {
    private Collection<?> a;
    private String expected;
    
    public TCollectionsToOrderedListStringTest(Collection<?> a, String expected) {
	this.a = a;
	this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection input() {
	ArrayList<Integer> a1 = new ArrayList<Integer>();
	ArrayList<Integer> a2 = new ArrayList<Integer>();
	a2.add(1);
	a2.add(2);
	return Arrays.asList(new Object[][] {
		{a1, ""},
		{a2, "1.1\n2.2\n"}, 
		{null, ""}
	    }
);
    }

    @Test
    public void testToOrderedListString() {
	assertEquals(expected, TCollections.toOrderedListString(a));
    }
}
