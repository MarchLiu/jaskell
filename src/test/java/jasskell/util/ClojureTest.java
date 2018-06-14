package jasskell.util;

import clojure.lang.IFn;
import clojure.lang.RT;
import jaskell.util.ClojureRuntime;
import org.junit.Assert;
import org.junit.Test;

public class ClojureTest {
    @Test
    public void unionTest(){
        ClojureRuntime runtime = new ClojureRuntime();
        runtime.require("clojure.set");
        IFn join = runtime.var("clojure.set", "union");
        Object x = runtime.read("#{:a :b :c}");
        Object y = runtime.read("#{:d}");
        Object z = join.invoke(x, y);
        Assert.assertEquals(z, RT.readString("#{:a :b :c :d}"));
    }
}
