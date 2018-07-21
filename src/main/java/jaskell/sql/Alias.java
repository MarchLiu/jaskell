package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.List;

public class Alias implements Directive {
    Query _query;
    private Name _name;

    public Alias(String name){
        this._name = new Name(name);
    }

    @Override
    public String script() {
        return String.format("(%s) as %s",
                _query.script(),
                _name.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _query.parameters();
    }

    public Join join(Directive other){
        var re = new Join();
        re._prefix = this;
        re._join = other;
        return re;
    }

    public Left left(){
        var re = new Left();
        re._prefix = this;
        return re;
    }

    public Right right(){
        var re = new Right();
        re._prefix = this;
        return re;
    }

    public Full full(){
        var re = new Full();
        re._prefix = this;
        return re;
    }

    public Inner inner(){
        var re = new Inner();
        re._prefix = this;
        return re;
    }

    public Cross cross(){
        var re = new Cross();
        re._prefix = this;
        return re;
    }
}
