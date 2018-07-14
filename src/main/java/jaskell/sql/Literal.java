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


}
