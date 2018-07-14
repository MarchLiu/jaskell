package jaskell.sql;

import jaskell.script.Directive;

import java.util.ArrayList;
import java.util.List;

public class Name extends Literal {

    protected Name(){}

    public Name(String name) {
        _literal = name;
    }

    public String name(){
        return _literal;
    }

    protected void name(String n){
        _literal = n;
    }

    public Alias as(String name){
        var re = new Name.Alias(name);
        re.name(_literal);
        return re;
    }

    public static class Alias extends Name {
        private String _alias;

        public Alias(String alias) {
            this._alias = alias;
        }

        public String alias(){
            return _alias;
        }

        @Override
        public String script() {
            return String.format("%s as %s", name(), alias());
        }

    }
}
