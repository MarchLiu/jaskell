package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.List;

public class Group implements Directive {
    Directive _prefix;

    @Override
    public String script() {
        return String.format("%s group", _prefix.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _prefix.parameters();
    }

    public jaskell.sql.Group.By by(String names){
        var re =  new jaskell.sql.Group.By(names);
        re._prefix = this;
        return re;
    }

    public jaskell.sql.Group.By by(String... names){
        var re = new jaskell.sql.Group.By(names);
        re._prefix = this;
        return re;
    }

    public jaskell.sql.Group.By by(Directive ... names){
        var re = new jaskell.sql.Group.By(names);
        re._prefix = this;
        return re;
    }

    public Order order() {
        var re = new Order();
        re._prefix = this;
        return re;
    }

    public static class By extends jaskell.sql.By {
        public By(String names){
            super(names);
        }

        public By(String... names){
            super(names);
        }

        public By(Directive ... names){
            super(names);
        }

        public Having having(Predicate predicate){
            var re = new Having(predicate);
            re._by = this;
            return re;
        }
    }

}
