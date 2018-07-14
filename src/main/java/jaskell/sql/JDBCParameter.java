package jaskell.sql;

import jaskell.script.Directive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCParameter<T> extends jaskell.script.Parameter<T> {
    public JDBCParameter(Object key, Class<T> cls){
        super("?", key, cls);
    }

}
