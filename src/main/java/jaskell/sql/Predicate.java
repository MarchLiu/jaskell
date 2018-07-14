package jaskell.sql;

import jaskell.script.Directive;

public abstract class Predicate implements Directive {
    public Predicate and(Predicate predicate){
        var re = new And();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate or(Predicate predicate){
        var re = new Or();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate eq(Directive predicate){
        var re = new Equal();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate ne(Directive predicate){
        var re = new NotEqual();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate gt(Directive predicate){
        var re = new Great();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate ls(Directive predicate){
        var re = new Less();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate gte(Directive predicate){
        var re = new GreateOrEqual();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate lse(Directive predicate){
        var re = new LessOrEqual();
        re._left = this;
        re._right = predicate;
        return re;
    }

    public Predicate like(Directive predicate){
        var re = new LessOrEqual();
        re._left = this;
        re._right = predicate;
        return re;
    }
}
