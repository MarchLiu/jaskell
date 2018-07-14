package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;

public class With extends Query {
    private Name _name;

    With(){

    }

    With(String name){
        _name = new Name(name);
    }

    With(Name name){
        _name = name;
    }

    public Name name(){
        return _name;
    }

    public With name(String name){
        _name = new Name(name);
        return this;
    }

    public With name(Name name){
        _name = name;
        return this;
    }

    public As as(Query query){
        var re =  new As(query);
        re._prefix = this;
        return re;
    }

    public Recursive recursive(){
        var re = new Recursive();
        re._prefix = this;
        return re;
    }

    @Override
    public String script() {
        return String.format("with %s", _name.script());
    }

    @Override
    public List<Parameter> parameters() {
        return new ArrayList<>();
    }

    public static class Recursive extends With {
        With _prefix;

        Recursive(){

        }

        @Override
        public String script() {
            return String.format("with recursive %s", _prefix._name.script());
        }
    }

    public static class As implements Directive {
        Directive _prefix;
        Query _query;
        As(Query query){
            _query = query;
        }

        @Override
        public String script() {
            return String.format("%s as (%s)", _prefix.script(), _query.script());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            re.addAll(_query.parameters());
            return re;
        }

        public CommonTableExpression cte(String name){
            var re = new CommonTableExpression();
            re._prefix = this;
            re._name = new Name(name);
            return re;
        }

        public CommonTableExpression cte(Name name){
            var re = new CommonTableExpression();
            re._prefix = this;
            re._name = name;
            return re;
        }

        public CommonTableQuery query(Query query){
            var re = new CommonTableQuery();
            re._prefix = this;
            re._query = query;
            return re;
        }

        public With.Select select(String names){
            var re = new With.Select(names);
            re._prefix = this;
            return re;
        }

        public With.Select select(String... names){
            var re = new With.Select(names);
            re._prefix = this;
            return re;
        }

        public With.Select select(Directive... names){
            var re = new With.Select(names);
            re._prefix = this;
            return re;
        }

        public With.Insert insert() {
            var re = new With.Insert();
            re._prefix = this;
            return re;
        }

        public With.Update update(String name) {
            var re = new With.Update(name);
            re._prefix = this;
            return re;
        }

        public With.Update update(Name name) {
            var re = new With.Update(name);
            re._prefix = this;
            return re;
        }

        public With.Delete delete() {
            var re = new With.Delete();
            re._prefix = this;
            return re;
        }

    }

    public static class Select extends jaskell.sql.Select {
        Directive _prefix;

        Select(String names){
            super(names);
        }

        Select(String... names){
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
    }

    public static class Insert extends jaskell.sql.Insert {
        Directive _prefix;

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
    }

    public static class Delete extends jaskell.sql.Delete {
        Directive _prefix;

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

    }

    public static class Update extends jaskell.sql.Update {
        Directive _prefix;

        Update(String name) {
            super(name);
        }

        Update(Name name) {
            super(name);
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
    }

    public static class CommonTableExpression implements Directive {
        Directive _prefix;
        Name _name;

        public As as(Query query){
            var re =  new As(query);
            re._prefix = this;
            return re;
        }

        @Override
        public String script() {
            return String.format("%s, %s", _prefix.script(), _name.script());
        }

        @Override
        public List<Parameter> parameters() {
            return _prefix.parameters();
        }
    }

    public static class CommonTableQuery extends Query {
        Directive _prefix;
        Query _query;

        @Override
        public String script() {
            return String.format("%s %s", _prefix.script(), _query.script());
        }

        @Override
        public List<Parameter> parameters() {
            var re = _prefix.parameters();
            re.addAll(_query.parameters());
            return re;
        }
    }

}
