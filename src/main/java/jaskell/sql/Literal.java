package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Literal extends Predicate {
    protected Literal(){}
    String _literal;

    Literal(int value){
        _literal = Integer.valueOf(value).toString();
    }

    Literal(long value){
        _literal = Long.valueOf(value).toString();
    }

    Literal(Number value){
        _literal = value.toString();
    }
    Literal(String value){ _literal = value; }
    Literal(Object value){
        _literal = String.format("%s", value);
    }

    @Override
    public String script() {
        return _literal;
    }

    @Override
    public List<Parameter> parameters() {
        return new ArrayList<>();
    }

    public Alias as(String name){
        var re = new Literal.Alias(name);
        re._prefix = this;
        return re;
    }

    public Alias as(Name name){
        var re = new Literal.Alias(name.name());
        re._prefix = this;
        return re;
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

    public static class Alias implements Directive {
        Name _name;
        Directive _prefix;

        Alias(String alias) {
            this._name = new Name(alias);
        }

        Alias(Literal alias) {
            this._name = new Name(alias.script());
        }

        public String alias(){
            return _name.script();
        }

        @Override
        public String script() {
            return String.format("%s as %s", _prefix.script(), _name.name());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            re.addAll(_name.parameters());
            return re;
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
}
