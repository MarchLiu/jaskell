package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.List;

public interface ThenSelect {
    default Select select(){
        return new Select();
    }

    default Select select(String names) {
        return new Select(names);
    }

    default Select select(String ... names) {
        return new Select(names);
    }

    default Select select(Directive ... names) {
        return new Select(names);
    }

    class Select extends jaskell.sql.Select  {
        Directive _prefix;

        Select(){
            super();
        }

        Select(String names){
            super(names);
        }

        Select(String ... names){
            super(names);
        }

        Select(Directive... names){
            super(names);
        }

        @Override
        public String script() {
            return String.format("%s %s", _prefix.script(), super.script());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            re.addAll(super.parameters());
            return re;
        }

        public Into.Select.From from(String name){
            var re = new From();
            re._from = new Name(name);
            re._select = this;
            return re;
        }

        public Into.Select.From from(Directive f) {
            var re = new From();
            re._select = this;
            re._from = f;
            return re;
        }
    }
}
