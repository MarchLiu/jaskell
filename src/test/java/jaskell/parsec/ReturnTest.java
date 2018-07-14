package jaskell.parsec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ReturnTest extends Base {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }
    /**
     * Method: script(State<E> s)
     */
    @Test
    public void simple() throws Exception {
        State<Character, Integer, Integer> state = newState("Hello World");
        Return<BigDecimal, Character> returns = new Return<>(new BigDecimal("3.1415926"));
        Integer idx = state.status();
        BigDecimal re = returns.parse(state);
        assertEquals(re, new BigDecimal("3.1415926"));
        assertEquals(state.status(), idx);
    }
}
