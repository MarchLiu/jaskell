package jaskell.sql;

import jaskell.script.Directive;

public abstract class Query extends Statement {

    public Alias as(String name){
        var re = new Alias(name);
        re._query = this;
        return re;
    }

    public Union union(){
        var re = new Union();
        re._prefix = this;
        return re;
    }

    public Union union(Query query){
        var re = new Union();
        re._prefix = this;
        re._query = query;
        return re;
    }

    public Limit limit(int l){
        var re = new Limit(l);
        re._prefix = this;
        return re;
    }

    public Limit limit(Directive l){
        var re = new Limit(l);
        re._prefix = this;
        return re;
    }

    public Offset offset(int o){
        var re = new Offset(o);
        re._prefix = this;
        return re;
    }

    public Offset offset(Directive o){
        var re = new Offset(o);
        re._prefix = this;
        return re;
    }

}
