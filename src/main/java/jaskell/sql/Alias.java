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
}
