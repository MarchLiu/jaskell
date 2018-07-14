package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Delete implements Directive {
    @Override
    public String script() {
        return "delete";
    }

    @Override
    public List<Parameter> parameters() {
        return new ArrayList<>();
    }

    public Delete.From from(String name) {
        return new Delete.From(name);
    }

    public Delete.From from(Directive name) {
        return new Delete.From(name);
    }

    public static class From extends Statement {
        Directive _from;

        public From(String name){
            _from = new Name(name);
        }

        public From(Directive name){
            _from = name;
        }

        @Override
        public String script() {
            return String.format("delete from %s", _from.script());
        }

        @Override
        public List<Parameter> parameters() {
            return _from.parameters();
        }

        public Using using(String names) {
            var re  = new Using(names);
            re._prefix = this;
            return re;
        }

        public Using using(String... names) {
            var re  = new Using(names);
            re._prefix = this;
            return re;
        }

        public Using using(Directive... names) {
            var re  = new Using(names);
            re._prefix = this;
            return re;
        }

        public Delete.Where where(Predicate predicate){
            var re = new Delete.Where();
            re._prefix = this;
            re._predicate = predicate;
            return re;
        }

        public Returning returning(String names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }

        public Returning returning(String... names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }

        public Returning returning(Directive... names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }
    }

    public static class Where extends Statement{
        Directive _prefix;
        Predicate _predicate;

        @Override
        public String script() {
            return String.format("%s where %s", _prefix.script(), _predicate.script());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            re.addAll(_predicate.parameters());
            return re;
        }

        public Returning returning(String names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }

        public Returning returning(String... names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }

        public Returning returning(Directive... names){
            var re = new Returning(names);
            re._prefix = this;
            return re;
        }
    }
}
