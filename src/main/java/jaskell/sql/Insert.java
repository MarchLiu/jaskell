package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Insert implements Directive {
    @Override
    public String script() {
        return "insert";
    }

    @Override
    public List<Parameter> parameters() {
        return new ArrayList<>();
    }

    public Into into(String name) {
        return new Into(name);
    }

    public Into into(String name, String fields) {
        return new Into(name, fields);
    }

    public Into into(String name, String ... fields) {
        return new Into(name, fields);
    }

    public Into into(String name, Directive ... fields) {
        return new Into(name, fields);
    }
}
