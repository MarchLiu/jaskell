package jaskell.parsec;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.EOFException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TextTest extends Base {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parse(State<E> s)
     */
    @Test
    public void simple() throws Exception {
        State<Character, Integer, Integer> state = newState("Hello World");
        Text s = new Text("Hello World");
        String a =  s.parse(state);
        assertEquals(a,"Hello World");
    }

    @Test
    public void less() throws Exception {
        State<Character, Integer, Integer> state = newState("Hello World");
        Text s = new Text("Hello");
        String a =  s.parse(state);
        assertEquals(a,"Hello");
    }

    @Test
    public void more() throws Exception {
        State<Character, Integer, Integer> state = newState("Hello");
        Text s = new Text("Hello world");
        try {
            s.parse(state);
            fail("expect parse failed because test data too large.");
        } catch (EOFException e) {
            assertTrue(true);
        }
    }
}
