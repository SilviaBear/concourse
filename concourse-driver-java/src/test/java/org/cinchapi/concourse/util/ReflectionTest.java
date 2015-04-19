/*
 * Copyright (c) 2013-2015 Cinchapi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cinchapi.concourse.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link Reflection} utility class.
 * 
 * @author Jeff Nelson
 */
@SuppressWarnings("unused")
public class ReflectionTest {
    
    @Test
    public void testInheritedGetValueFromSuperClass(){
        int expected = Random.getInt();
        B b = new B(expected);
        Assert.assertEquals("default", Reflection.get("string", b));
    }
    
    @Test
    public void testCallSuperClassMethod(){
        B b = new B(Random.getInt());
        Assert.assertEquals("default", Reflection.call(b, "string"));
        Assert.assertEquals("defaultdefaultdefault", Reflection.call(b, "string", 3));
    }
    
    @Test
    public void testGetValueFromClassA(){
        String expected = Random.getString();
        A a = new A(expected);
        Assert.assertEquals(expected, Reflection.get("string", a));
    }
    
    @Test
    public void testCallMethodInClassA(){
        String expected = Random.getString();
        A a = new A(expected);
        Assert.assertEquals(expected, Reflection.call(a, "string"));
        Assert.assertEquals(expected+expected+expected, Reflection.call(a, "string", 3));
    }
    
    @Test
    public void testCallMethodInClassB(){
        int expected = Random.getInt();
        B b = new B(expected);
        Assert.assertEquals((long) (expected * 10), Reflection.call(b, "integer", 10));
        
    }
    
    @Test
    public void testGetValueFromClassB(){
        int expected = Random.getInt();
        B b = new B(expected);
        Assert.assertEquals(expected, Reflection.get("integer", b));
    }
    
    @Test(expected = RuntimeException.class)
    public void testAttemptToGetValueForNonExistingFieldThrowsException(){
        A a = new A(Random.getString());
        Reflection.get("foo", a);
    }

    @Test
    public void testNullValue(){
        A a = new A();
        Assert.assertNull(Reflection.get("string", b));
    }

    @Test
    public void testGetShadowedField(){
        B b = new B(Random.getInt());
        Assert.assertEquals("ben", Reflection.get("string", b));
    }

    @Test
    public void testPrimitiveArray() {
        C c = new C(false);
        Assert.assertArrayEquals({1, 2}, Reflection.get("intArray", c));
    }

    @Test
    public void testEmptyArray() {
        C c = new C(true);
        int[] expected = {};
        Assert.assertArrayEquals(expected, Reflection.get("intArray", c));
    }

    @Test
    public void testObjectArray() {
        D d = new D();
        String[] expected = {"ben"};
        Assert.assertArrayEquals(expected, Reflection.get("stringArray", d));
    }
    
    private static class A {
        
        private final String string;

        public A() {
        }
        public A(String string){
            this.string = string;
        }
        
        private String string(){
            return string;
        }
        
        private String string(int count){
            String result = "";
            for(int i = 0; i < count; i++){
                result+= string;
            }
            return result;
        }
    }
    
    private static class B extends A {
        
        private final int integer;
        private final String string = "ben";
        
        public B(int integer){
            super();
            this.integer = integer;
        }

        private long integer(int multiple){
            return multiple * integer;
        }
    }

    private static class C {
        private int[] intArray;

        public C(boolean isEmpty){
            if(isEmpty) {
                intArray = {};
            }
            else{
                intArray = {1, 2};
            }
        }

    }

    private static class D {
        private String[] stringArray;

        public D(){
            stringArray = {"ben"};
        }
    }

}
