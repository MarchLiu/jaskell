package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class Statement implements Directive {

    public PreparedStatement prepare(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.script());
        return statement;
    }

    public boolean execute(Connection connection) throws SQLException, IllegalStateException {
        PreparedStatement statement = connection.prepareStatement(this.script());
        List<Parameter> parameters = parameters();
        for (Parameter parameter: parameters){
            if(!parameter.confirmed()){
                throw new IllegalStateException(String.format("parameter %s has not value", parameter.key()));
            }
        }
        syncParameters(statement);
        return statement.execute();
    }

    public <T> void setParameter(Object key, T value) throws IllegalArgumentException{
        var flag = false;
        for (Parameter parameter:parameters()) {
            if (Objects.equals(parameter.key(), key)) {
                parameter.value(value);
                flag = true;
            }
        }
        if(!flag){
            throw new IllegalArgumentException(String.format("parameter named %s not found", key));
        }
    }

    public void clear(PreparedStatement statement) throws SQLException {
        statement.clearParameters();
    }

    public void syncParameters(PreparedStatement statement) throws SQLException {
        clear(statement);
        var params = parameters();
        setOrder(params);
        for (jaskell.script.Parameter parameter: params) {
            //TODO: overload by parameter.valueClass
            statement.setObject(parameter.order(), parameter.value());
        }

    }

    void setOrder(List<jaskell.script.Parameter> parameters){
        for (int i = 0; i < parameters.size(); i++) {
            parameters.get(i).order(i+1);
        }
    }
}
