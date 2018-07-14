package jaskell.sql;


import jaskell.script.Directive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Values extends Statement {
    Directive _insert;
    List<Directive> _fields = new ArrayList<>();

    public Values(String fields){
        _fields.addAll(
                Arrays.stream(fields.split(",")).map(String::trim).map(Name::new).collect(Collectors.toList()));
    }

    public Values(String ... fields){
        _fields.addAll(
                Arrays.stream(fields).map(String::trim).map(Name::new).collect(Collectors.toList()));
    }

    public Values(Directive... fields){
        _fields.addAll(Arrays.asList(fields));
    }

    public Returning returning(String names){
        var re =  new Returning(names);
        re._prefix = this;
        return re;
    }

    public Returning returning(String ... names){
        var re =  new Returning(names);
        re._prefix = this;
        return re;
    }

    public Returning returning(Directive names){
        var re =  new Returning(names);
        re._prefix = this;
        return re;
    }

    @Override
    public String script() {
        return String.format("%s values(%s)",
                _insert.script(),
                _fields.stream().map(Directive::script).collect(Collectors.joining(", ")));
    }

    @Override
    public List<jaskell.script.Parameter> parameters() {
        var re =  _insert.parameters();
        _fields.forEach(field->re.addAll(field.parameters()));
        return re;
    }
}
