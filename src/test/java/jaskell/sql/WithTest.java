package jaskell.sql;

import org.junit.Assert;
import org.junit.Test;
import static jaskell.sql.SQL.*;

public class WithTest {

    @Test
    public void BasicTest0(){
        final var script =
                "with t1 as (select n from t), t2 as (select m from t) select m * n from t1 join t2 on t1.n != t2.m";
        var query = with("t1").as(select("n").from("t"))
                .cte("t2").as(select("m").from("t"))
                .select(l("m * n")).from("t1").join(n("t2")).on(n("t1.n").ne(n("t2.m")));
        Assert.assertEquals(script, query.script());
    }

    @Test
    public void BasicTest1(){
        final var script =
                "with recursive t(f) as (select 1 union select f+1 from t where f < 100) select f from t";
        var query = with().recursive().name("t(f)")
                .as(select(l(1)).union().select(l("f+1")).from("t").where(n("f").lt(100)))
                .select("f").from("t");
    }
}
