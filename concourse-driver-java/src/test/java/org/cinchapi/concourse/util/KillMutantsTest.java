package org.cinchapi.concourse.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class KillMutantsTest {
    @Test
    public void mutant1() {
	assertNull(StringsMutated1.tryParseNumber("1.12E"));
    }

    @Test
    public void mutant2() {
	assertNull(StringsMutated2.tryParseNumber(null));
    }
    
    @Test
    public void mutant3() {
	assertEquals(new Double(1.2), StringsMutated3.tryParseNumber("1"));
    }
    
    @Test
    public void mutant4() {
	assertNull(StringsMutated4.tryParseNumber("1-2"));
    }

    @Test
    public void mutant5() {
	assertNull(StringsMutated5.tryParseNumber("D"));
    }
    
    @Test
    public void mutant6() {
	assertNull(StringsMutated6.tryParseNumber("FWEFRWG"));
    }

    @Test
    public void mutant7() {
	assertEquals(new Double(123.5), StringsMutated7.tryParseNumber("123.5D"));
    }

    @Test
    public void mutant8() {
	assertEquals(new Float(123.5), StringsMutated8.tryParseNumber("123.5"));
    }

    @Test
    public void mutant9() {
	assertNull(StringsMutated9.tryParseNumber("DDDD"));
    }
}
