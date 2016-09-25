package jaskell.parsec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class TryTest extends Base {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void simple() throws Exception {
        List<String> data = Arrays.asList("Hello", "World");

        State<String, Integer, Integer> state = new BasicState<>(data);
        Integer idx = state.status();
        Try<String, String> tryIt = new Try<>(new Eq<>("Hello"));

        String re = tryIt.parse(state);

        assertEquals(re, "Hello");
        assertNotEquals(idx, state.status());
    }
    @Test
    public void rollback() throws Exception {
        List<String> data = Arrays.asList("Hello", "World");
        BasicState<String> state = new BasicState<>(data);
        Integer idx = state.status();
        Try<String, String> tryIt = new Try<>(new Eq<>("hello"));

        try{
            String re = tryIt.parse(state);
            fail("Expect a error for Hello.");
        }catch(Exception e){
            assertEquals(idx, state.status());
        }
    }
}
