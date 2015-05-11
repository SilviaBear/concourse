package org.cinchapi.concourse.shell;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.*;

import java.util.UUID;
import java.util.Random;

import org.cinchapi.concourse.Concourse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTest {

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
    public void testAdd() {
        Random rn = new Random();
        int times = rn.nextInt(1000) + 1;
        operation op = new add();
        for(int i = 0; i < times; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            try {
                //Simulate duplicated key
                if(rn.nextInt() % 10 == 0) {
                    assertTrue(cash.evaluate(op.returnPat(key, value, rn.nextInt() % i)).contains("false"));
                }
                else {
                    assertTrue(cash.evaluate(op.returnPat(key, value, i)).contains("true"));
                    //Random test positive and negative verification
                    if(rn.nextInt() % 3 == 0) {
                        assertTrue(cash.evaluate(getVerifyString(key, value, i)).contains(op.verifyExpected(key, value, UUID.randomUUID().toString(), i)));
                    }
                    else {
                        assertTrue(cash.evaluate(getVerifyString(key, value, i)).contains(op.verifyExpected(key, value, value, i)));
                    }
                    assertTrue(cash.evaluate(getGetString(key, i)).contains(value));
                }
            }
            catch (IrregularEvaluationResult e) {
                e.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    @Test
    public void testRemove() {
        Random rn = new Random();
        int times = rn.nextInt(1000) + 1;
        operation op = new remove();
        operation addOp = new add();
        for(int i = 0; i < times; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            try {
                //Simulate invalid remove case
                if(rn.nextInt() % 3 == 0) {
                    assertTrue(cash.evaluate(op.returnPat(key, value, i)).contains("false"));
                }
                else {
                    assertTrue(cash.evaluate(addOp.returnPat(key, value, i)).contains("true"));
                    assertTrue(cash.evaluate(getVerifyString(key, value, i)).contains(op.verifyExpected(key, value, value, i)));
                    assertFalse(cash.evaluate(getGetString(key, i)).contains(value));
                }
            }
            catch(IrregularEvaluationResult e) {
                e.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
       
    }

    @Test
    public void testSet() throws Exception {
        Random rn = new Random();
        int times = rn.nextInt(1000) + 1;
        operation op = new set();
        operation addOp = new add();
        for(int i = 0; i < times; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            try {
                //Simulate invalid update
                if(rn.nextInt() % 4 == 0) {
                    assertTrue(cash.evaluate(op.returnPat(key, value, i)).contains("false"));
                }
                else {
                    assertTrue(cash.evaluate(addOp.returnPat(key, value, i)).contains("true"));
                    //Randomly test positive and negative verification
                    if(rn.nextInt() % 3 == 0) {
                        assertTrue(cash.evaluate(getVerifyString(key, value, i)).contains(op.verifyExpected(key, value, UUID.randomUUID().toString(), i)));
                    }
                    else {
                        assertTrue(cash.evaluate(getVerifyString(key, value, i)).contains(op.verifyExpected(key, value, value, i)));
                    }
                    assertTrue(cash.evaluate(getGetString(key, i)).contains(value));
                }
            }
            catch (IrregularEvaluationResult e) {
                e.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(1000);
            }
            catch (InterruptedException e) {
            }
        }
    }

    @Test
    public void stressTest() {
        Random rn = new Random();
        int times = rn.nextInt(100000) + 1;
        operation opset = new set();
        operation opadd = new add();
        operation opremove = new remove();
        operation op;
        for(int i = 0; i < times; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            int ben = rn.nextInt();
            if(ben % 3 == 0) {
                op = opset;
            }
            else if(ben % 3 == 1) {
                op = opadd;
            }
            else {
                op = opremove;
            }
            try {
                cash.evaluate(op.returnPat(key, value, i));
            }
            catch(IrregularEvaluationResult e) {
                e.printStackTrace();
            }
        }
    }

    private String getVerifyString(String key, String value, int record) {
        return "verify \"" + key + ", \"" + value + "\", " + record;
    }

    private String getGetString(String key, int record) {
        return "get \"" + key + ", " + record;
    }
    
    interface operation {

        String returnPat(String key, String value, int record);
        //Return regex pattern a verify transaction should return based on the previous transaction
        String verifyExpected(String key, String inputValue, String queryValue, int record);
    }

    class remove implements operation {

        @Override
        public String returnPat(String key, String value, int record) {
            return "remove \"" + key + ", \"" + value + "\", " + record;
        }

        @Override
        public String verifyExpected(String key, String inputValue, String queryValue, int record) {
            return "false";
        }
    }
    class add implements operation {
        @Override
        public String returnPat(String key, String value, int record) {
            String returnString = "add ";
            returnString += "\"" + key + ", \"" + value + "\", ";
            returnString += record;
            return returnString;
        }

        @Override
        public String verifyExpected(String key, String inputValue, String queryValue, int record) {
            return inputValue == queryValue? "true" : "false";
        }
    }

    class set implements operation {
        
        @Override
        public String returnPat(String key, String value, int record) {
            return "set \"" + key + ", \"" + value + "\", " + record; 
        }

        public String verifyExpected(String key, String inputValue, String queryValue, int record) {
            return inputValue == queryValue? "true" : "false";
        }
    }
}
