package org.cinchapi.concourse.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class StringParseNumberTest {
    /*
    stream ::= s i do d double 
    s = "-"^[0-1] (repeat "-" 0 or 1 time)
    i = digit*
    d = digitdigit*
    double = "D"^(0-1) (repeat "D" 0 or 1 time)
    do ::= "."^(0-1) (repeat "." 0 or 1 time)
    digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
    */
    @Test
    public void testPositiveDouble() {
	assertEquals(new Double(1.12), Strings.tryParseNumber("1.12D"));
    }

    @Test
    public void testNegativeDouble() {
	assertEquals(new Double(-1.12), Strings.tryParseNumber("-1.12D"));
    }

    @Test
    public void testPositiveFloat() {
	assertEquals(new Float(3.45), Strings.tryParseNumber("3.45"));
    }
    
    @Test
    public void testNegativeFloat() {
	assertEquals(new Float(-3.45), Strings.tryParseNumber("-3.45"));
    }

    @Test
    public void testPositiveInteger() {
	assertEquals(new Integer(67890), Strings.tryParseNumber("67890"));
    }

    @Test
    public void testNegativeInteger() {
	assertEquals(new Integer(-67890), Strings.tryParseNumber("-67890"));
    }

    @Test
    public void testNegativeFloat2() {
	assertEquals(new Float(-0.12), Strings.tryParseNumber("-.12"));
    }

    @Test
    public void testPositiveFloat2() {
	assertEquals(new Float(3.0), Strings.tryParseNumber(".1"));
    }
    
    @Test
    public void testPositiveLong() {
	assertEquals(new Long(2147483647), Strings.tryParseNumber("4294967259"));
    }
    
    @Test
    public void testNegativeLong() {
	assertEquals(new Long(-2147483647), Strings.tryParseNumber("-2147483647"));
    }
}
