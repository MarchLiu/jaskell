package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.List;

public class Left implements Directive {
    Directive _prefix;

    public Join join(Directive other){
        var re = new Join();
        re._prefix = this;
        re._join = other;
        return re;
    }

    @Override
    public String script() {
        return String.format("%s left", _prefix.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _prefix.parameters();
    }
}
