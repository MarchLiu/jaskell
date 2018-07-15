package jaskell.sql;

import jaskell.parsec.Option;
import jaskell.script.Directive;

import java.sql.*;
import java.util.Optional;

public abstract class Query extends Statement {

    public Alias as(String name){
        var re = new Alias(name);
        re._query = this;
        return re;
    }

    public Union union(){
        var re = new Union();
        re._prefix = this;
        return re;
    }

    public Union union(Query query){
        var re = new Union();
        re._prefix = this;
        re._query = query;
        return re;
    }

    public Limit limit(int l){
        var re = new Limit(l);
        re._prefix = this;
        return re;
    }

    public Limit limit(Directive l){
        var re = new Limit(l);
        re._prefix = this;
        return re;
    }

    public Offset offset(int o){
        var re = new Offset(o);
        re._prefix = this;
        return re;
    }

    public Offset offset(Directive o){
        var re = new Offset(o);
        re._prefix = this;
        return re;
    }

    public ResultSet query(Connection conn) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement(this.script())){
            syncParameters(statement);
            return statement.executeQuery();
        }
    }

    public Optional scalar(Connection conn) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement(this.script());
            ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()){
                return Optional.of(resultSet.getObject(1));
            } else {
                return Optional.empty();
            }
        }
    }

    public <T> Optional<T> scalar(Connection conn, Class<T> cls) throws SQLException {
        try(PreparedStatement statement = conn.prepareStatement(this.script());
            ResultSet resultSet = statement.executeQuery()){
            if (resultSet.next()){
                if(resultSet.getObject(1)!=null) {
                    return Optional.of(cls.cast(resultSet.getObject(1)));
                }
            }
            return Optional.empty();
        }
    }
}
