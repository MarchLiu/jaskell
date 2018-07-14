package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Update implements Directive {
    private Name _table;
    public Update(String name){
        this._table = new Name(name);
    }

    public Update(Name name){
        this._table = name;
    }

    public Update.Set set(String field, Directive value){
        var re = new Update.Set(field, value);
        re._prefix = this;
        return re;
    }

    public Update.Set set(Directive field, Directive value){
        var re = new Update.Set(field, value);
        re._prefix = this;
        return re;
    }

    @Override
    public String script() {
        return String.format("update %s", _table.script());
    }

    @Override
    public List<Parameter> parameters() {
        return _table.parameters();
    }

    public class Set extends Query {
        private Directive _prefix;
        private List<Equation> _sets = new ArrayList<>();
        Set(String field, Directive value){
            this._sets.add(new Equation(new Name(field), value));
        }

        public Set(Directive field, Directive value){
            this._sets.add(new Equation(field, value));
        }

        public Set set(String field, Directive value){
            this._sets.add(new Equation(field, value));
            return this;
        }

        public Set set(Directive field, Directive value){
            this._sets.add(new Equation(field, value));
            return this;
        }

        @Override
        public String script() {
            return String.format("%s set %s",
                    _prefix.script(),
                    _sets.stream().map(Directive::script).collect(Collectors.joining(", ")));
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            _sets.forEach(item -> re.addAll(item.parameters()));
            return re;
        }

        public Using using(String names){
            var re = new Using(names);
            re._prefix = this;
            return re;
        }

        public Using using(String... names){
            var re = new Using(names);
            re._prefix = this;
            return re;
        }

        public Using using(Directive ... names){
            var re = new Using(names);
            re._prefix = this;
            return re;
        }

        public Where where(Predicate predicate){
            var re = new Where(predicate);
            re._prefix = this;
            return re;
        }
    }

    public class Equation implements Directive {
        Directive _field;
        Directive _value;

        Equation(String field, Directive value){
            this._field = new Name(field);
            this._value = value;
        }

        Equation(Directive field, Directive value){
            this._field = field;
            this._value = value;
        }

        @Override
        public String script() {
            return String.format("%s=%s", _field.script(), _value.script());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _field.parameters();
            re.addAll(_value.parameters());
            return re;
        }
    }
}
