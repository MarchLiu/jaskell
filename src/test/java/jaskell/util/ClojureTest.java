package jaskell.util;

import clojure.lang.IFn;
import clojure.lang.PersistentArrayMap;
import clojure.lang.RT;
import jaskell.util.CR;
import org.junit.Assert;
import org.junit.Test;

public class ClojureTest {
    @Test
    public void unionTest(){
        CR.require("clojure.set");
        IFn join = CR.var("clojure.set", "union");
        Object x = CR.read("#{:a :b :c}");
        Object y = CR.read("#{:d}");
        Object z = join.invoke(x, y);
        Assert.assertEquals(z, RT.readString("#{:a :b :c :d}"));
    }

    @Test
    public void slurpTest(){
        PersistentArrayMap config = (PersistentArrayMap)CR.loadEdn("src/test/test.edn");
        Assert.assertEquals(config.get(RT.readString(":path")), "/project/root");
    }
}
