package jaskell.parsec;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static jaskell.parsec.Txt.ch;

public class NewlineTest extends Base {
    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void simpleCrlf() throws Exception {
        State<Character, Integer, Integer> state = newState("\r\n");
        Parsec<String, Character> crlf = new Crlf();

        String re = crlf.parse(state);
        Assert.assertEquals(re, "\r\n");
    }

    /**
     * Method: script(State<E> s)
     */
    @Test
    public void simpleNl() throws Exception {
        State<Character, Integer, Integer> state = newState("\r\n");

        Parsec<Character, Character> enter = new Newline();

        Character c = ch('\r').then(enter).parse(state);

        Assert.assertEquals(c.charValue(), '\n');
    }
}
