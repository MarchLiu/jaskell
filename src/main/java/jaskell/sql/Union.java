package jaskell.sql;

import jaskell.script.Parameter;

import java.util.List;

public class Union extends Query {
    Query _prefix;
    Query _query;

    public Union(){

    }

    public Union(Query query){
        this._query = query;
    }

    public Union.All all(Query query){
        var re = new Union.All();
        re._prefix = this._prefix;
        re._query = query;
        return re;
    }

    @Override
    public String script() {
        return String.format("%s union %s", _prefix.script(), _query.script());
    }

    @Override
    public List<Parameter> parameters() {
        var re = _prefix.parameters();
        re.addAll(_query.parameters());
        return re;
    }

    public static class All extends Union{
        @Override
        public String script() {
            return String.format("%s union all %s", _prefix.script(), _query.script());
        }
    }
}
