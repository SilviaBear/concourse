package org.cinchapi.concourse.util;

import java.io.File;

import java.util.Collection;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NaturalSorterParameterizedTest {
    int expected;
    String a;
    String b;
    
    
    public NaturalSorterParameterizedTest(String a, String b, int expected) {
	this.expected = expected;
	this.a = a;
	this.b = b;
    }

    @Parameterized.Parameters
    public static Collection input() {
	return Arrays.asList(new Object[][] {
		{"ab", "a", -1},
		{"ab", "ab", 0},
		{"a1", "a2", -1},
		{"12", "13", -1}
	    });
    }

    @Test
    public void testCompare() {
	assertEquals(expected, NaturalSorter.INSTANCE.compare(new File(a), new File(b)));
    }
}
