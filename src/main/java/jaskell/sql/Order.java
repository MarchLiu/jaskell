package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.List;

public class Order implements Directive {
    Directive _prefix;

    @Override
    public String script() {
        return String.format("%s order", _prefix.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _prefix.parameters();
    }

    public jaskell.sql.Order.By by(String names){
        var re =  new jaskell.sql.Order.By(names);
        re._prefix = this;
        return re;
    }

    public jaskell.sql.Order.By by(String... names){
        var re = new jaskell.sql.Order.By(names);
        re._prefix = this;
        return re;
    }

    public jaskell.sql.Order.By by(Directive ... names){
        var re = new jaskell.sql.Order.By(names);
        re._prefix = this;
        return re;
    }

    public static class By extends jaskell.sql.By {
        By(String names){
            super(names);
        }

        By(String... names){
            super(names);
        }

        By(Directive ... names){
            super(names);
        }
    }
}

