package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Into implements Directive {
    Name _name;
    List<Directive> _fields = new ArrayList<>();

    public Into(String table){
        _name = new Name(table);
    }

    public Into(String table, String fields){
        _name = new Name(table);
        _fields.addAll(
                Arrays.stream(fields.split(",")).map(String::trim).map(Name::new).collect(Collectors.toList()));
    }

    public Into(String table, String ... fields){
        _name = new Name(table);
        _fields.addAll(
                Arrays.stream(fields).map(String::trim).map(Name::new).collect(Collectors.toList()));
    }

    public Into(String table, Directive ... fields){
        _name = new Name(table);
        _fields.addAll(Arrays.asList(fields));
    }

    public Into field(String field){
        _fields.add(new Name(field));
        return this;
    }

    public Into fields(String fields){
        _fields.addAll(
                Arrays.stream(fields.split(",")).map(String::trim).map(Name::new).collect(Collectors.toList()));
        return this;
    }

    public Into fields(String ... fields){
        _fields.addAll(
                Arrays.stream(fields).map(String::trim).map(Name::new).collect(Collectors.toList()));
        return this;
    }

    public Into fields(String table, Directive ... fields){
        _fields.addAll(Arrays.asList(fields));
        return this;
    }


    @Override
    public String script() {
        return String.format("insert into %s(%s)",
                _name.script(),
                _fields.stream().map(Directive::script).collect(Collectors.joining(", ")));
    }

    @Override
    public List<Parameter> parameters() {
        var re = new ArrayList<Parameter>();
        _fields.forEach(item->re.addAll(item.parameters()));
        return re;
    }

    public Values values(String vs) {
        var re = new Values(vs);
        re._insert = this;
        return re;
    }

    public Values values(String ... vs) {
        var re = new Values(vs);
        re._insert = this;
        return re;
    }

    public Values values(Directive ... directives) {
        var re = new Values(directives);
        re._insert = this;
        return re;

    }

    public Into.Select select(){
        var re = new Into.Select();
        re._into = this;
        return re;
    }

    public Into.Select select(String fields){
        var re = new Into.Select(fields);
        re._into = this;
        return re;
    }


    public static class Select extends jaskell.sql.Select  {
        Into _into;

        public Select(){
            super();
        }

        public Select(String names){
            super(names);
        }

        public Select(String ... names){
            super(names);
        }

        public Select(Directive ... names){
            super(names);
        }

        @Override
        public String script() {
            return String.format("%s %s", _into.script(), super.script());
        }

        @Override
        public List<jaskell.script.Parameter> parameters() {
            var re = _into.parameters();
            re.addAll(super.parameters());
            return re;
        }

        public Select.From from(String name){
            var re = new From();
            re._from = new Name(name);
            re._select = this;
            return re;
        }

        public Select.From from(Directive f) {
            var re = new From();
            re._select = this;
            re._from = f;
            return re;
        }
    }

}
