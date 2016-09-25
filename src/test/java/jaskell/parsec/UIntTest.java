package jaskell.parsec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UIntTest extends Base {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void simple() throws Exception {
        State<Character, Integer, Integer> state = newState("23413214");

        UInt uint = new UInt();

        String s = uint.parse(state);

        assertEquals(s,"23413214");
    }

    @Test
    public void stop() throws Exception {
        State<Character, Integer, Integer> state = newState("23413a214");

        UInt uint = new UInt();

        String s = uint.parse(state);

        assertEquals(s,"23413");
    }

    @Test
    public void fail() throws Exception {
        State<Character, Integer, Integer> state = newState("x2344");

        UInt uint = new UInt();

        try {
            String s = uint.parse(state);
            throw new Exception("Expect fail when no digit at start.");
        }catch (ParsecException e) {
            assertTrue(true);
        }
    }
}
