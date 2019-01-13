package jaskell.util;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.Keyword;
import clojure.lang.RT;
import clojure.lang.Var;

import java.net.URL;
import java.util.ResourceBundle;

public class CR {
    static private IFn req = RT.var("clojure.core", "require");
    static private IFn apply = RT.var("clojure.core", "apply");
    static private IFn slurpFn = RT.var("clojure.core", "slurp");
    static {
        req.invoke(RT.readString("clojure.edn"));
    }
    static public void require(String namespace) {
        req.invoke(RT.readString(namespace));
    }

    static public Object read(String code){
        return RT.readString(code);
    }

    static public Object invoke(String namespace, String name, Object ... args) {
        IFn func = RT.var(namespace, name).fn();
        return apply.invoke(func, args);
    }

    static public Var var(String namespace, String name) {
        return RT.var(namespace, name);
    }

    static public Object readEdn(String edn){
        return RT.var("clojure.edn", "read-string").fn().invoke(edn);
    }

    static public Object loadEdn(String path){
        return  readEdn(slurp(path));
    }

    static public String slurp(String path){
        return (String)slurpFn.invoke(path);
    }

    static public Keyword keyword(String k){
        return (Keyword) Clojure.read(":"+k);
    }
}
