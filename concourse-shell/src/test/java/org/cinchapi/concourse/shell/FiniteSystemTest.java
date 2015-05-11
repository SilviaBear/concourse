package org.cinchapi.concourse.shell;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

import org.cinchapi.concourse.Concourse;

/* grammer of the input of ConcourseShell.evaluate(String input):
   stream ::= OP1","value","key","record | OP2","key","record | OP3","record
   OP1 ::= add | remove | set | verify
   OP2 ::= fetch | get
   OP3 ::= describe
   value ::= chch*
   key ::= chch*
   record ::= digitdigit*
   ch ::= all ascii characters
   digit ::= "0"|"1"|"2"|"3"|"4"|"5"|"6"|"7"|"8"|"9"
   hard to reach even TSC, too many ascii characters
 */

public class FiniteSystemTest {
    ConcourseShell cash;
    
    @After
    public void closeConnection() throws Exception {
        cash.concourse.exit();
    }
    
    @Before
    public void setConnection() throws Exception {
        try {
            cash = new ConcourseShell();
            cash.concourse = Concourse.connect("127.0.0.1", 1717, "admin", "admin", "");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Test
    public void testAddToSameRecord() {
        String input = "add \"name\", \"brown\", 1";
        assertTrue(cash.evaluate(input).contains("true"));
        input = "add \"name\", \"brown\", 1";
        assertTrue(cash.evaluate(input).contains("false"));
        input = "get \"name\", 1";
        assertTrue(cash.evaluate(input).contains("brown"));
        input = "add \"name\", \"cony\", 1";
        assertTrue(cash.evaluate(input).contains("true"));
        input = "fetch \"name\", 1";
        assertTrue(cash.evaluate(input).contains("cony"));
        assertTrue(cash.evaluate(input).contains("brown"));
        input = "get \"name\", 1";
        assertTrue(cash.evaluate(input).contains("brown"));
    }

    @Test
    public void testAddToDifferentRecords() {
        String input = "add \"name\", \"brown\", 1";
        assertTrue(cash.evaluate(input).contains("true"));
        input = "add \"name\", \"brown\", 2";
        assertTrue(cash.evaluate(input).contains("true"));
    }

    @Test
    public void testSetRecord() {
        String input = "add \"name\", \"brown\", 1";
        assertTrue(cash.evaluate(input).contains("true"));
        input = "add \"name\", \"cony\", 1";
        assertTrue(cash.evaluate(input).contains("true"));
        input = "set \"name\", \"james\", 1";
        cash.evaluate(input);
        input = "fetch \"name\", 1";
        String result = cash.evaluate(input);
        assertFalse(result.contains("brown"));
        assertFalse(result.contains("cony"));
        assertTrue(result.contains("james"));
    }

    @Test
    public void testRemove() {
        String input = "add \"name\", \"brown\", 1";
        cash.evaluate(input);
        input = "add \"name\", \"cony\", 1";
        cash.evaluate(input);
        input = "remove \"name\", 1";
        cash.evaluate(input);
        String result = cash.evaluate(input);
        assertFalse(result.contains("brown"));
        assertFalse(result.contains("cony"));
    }

    @Test
    public void testDescribe() {
        String input = "add \"name\", \"brown\", 1";
        cash.evaluate(input);
        input = "add \"food\", \"salmon\", 2";
        String result = cash.evaluate(input);
        assertTrue(result.contains("name"));
        assertTrue(result.contains("food"));
    }
}

