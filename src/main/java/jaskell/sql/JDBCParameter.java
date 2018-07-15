package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCParameter<T> extends Parameter<T> {
    // TODO: constructors type safe;
    public JDBCParameter(Object key){
        super("?", key);
    }
    public JDBCParameter(Object key, Class<T> cls){
        super("?", key, cls);
    }
    // TODO: value setters type safe;
}
