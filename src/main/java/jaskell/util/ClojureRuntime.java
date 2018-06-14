package jaskell.util;

import clojure.lang.IFn;
import clojure.lang.RT;
import clojure.lang.Var;

public class ClojureRuntime {
    private IFn req = RT.var("clojure.core", "require");
    private IFn apply = RT.var("clojure.core", "apply");
    public ClojureRuntime require(String namespace) {
        req.invoke(RT.readString(namespace));
        return this;
    }

    public Object read(String code){
        return RT.readString(code);
    }

    public Object invoke(String namespace, String name, Object ... args) {
        IFn func = RT.var(namespace, name).fn();
        return apply.invoke(func, args);
    }

    public Var var(String namespace, String name) {
        return RT.var(namespace, name);
    }

}
