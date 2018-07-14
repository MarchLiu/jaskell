package jaskell.sql;

import jaskell.script.Directive;

import java.util.List;

public class Where extends Query {
    Directive _prefix;
    Predicate _predicate;

    Where(Predicate predicate){
        this._predicate = predicate;
    }

    @Override
    public String script() {
        return String.format("%s where %s", _prefix.script(), _predicate.script());
    }

    @Override
    public List<jaskell.script.Parameter> parameters() {
        var re = _prefix.parameters();
        re.addAll(_predicate.parameters());
        return re;
    }

    public Group group() {
        var re = new Group();
        re._prefix = this;
        return re;
    }

    public Order order() {
        var re = new Order();
        re._prefix = this;
        return re;
    }
}
