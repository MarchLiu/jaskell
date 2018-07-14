package jaskell.sql;

import jaskell.script.Parameter;

import java.util.List;

public class Not extends Predicate {
    Predicate _predicate;
    Not(Predicate predicate){
        _predicate = predicate;
    }

    @Override
    public String script() {
        return String.format("not(%s) ", _predicate.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _predicate.parameters();
    }
}
