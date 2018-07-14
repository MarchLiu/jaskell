package jaskell.sql;

import jaskell.script.Directive;
import jaskell.script.Parameter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SQL {
    public static Select select(){
        return new Select();
    }

    public static Select select(String names){
        return new Select(names);
    }

    public static Select select(String... names){
        return new Select(names);
    }

    public static Select select(Directive... names){
        return new Select(names);
    }

    public static Insert insert(){
        return new Insert();
    }

    public static Update update(String name){
        return new Update(name);
    }

    public static Update update(Name name){
        return new Update(name);
    }

    public static Delete delete(){
        return new Delete();
    }

    public static With with(){
        return new With();
    }

    public static With with(String name){
        return new With(name);
    }

    public static With with(Name name){
        return new With(name);
    }

    public static Name n(String name){
        return new Name(name);
    }

    public static Quot q(String name){
        return new Quot(name);
    }

    public static List<Quot> qs(String ... names){
        return Arrays.stream(names).map(Quot::new).collect(Collectors.toList());
    }

    public static Literal text(String content){
        return new Literal(content);
    }
    public static <T> Literal l(T v){
        return new Literal(v);
    }

    public static Predicate br(Predicate predicate){
        var re = new Brackets();
        re._segment = predicate;
        return re;
    }

    public static Parameter p(int index){
        return new JDBCParameter<>(index, Object.class);
    }

    public static <T> Parameter p(int index, Class<T> cls){
        return new JDBCParameter<>(index, cls);
    }

    public static Parameter p(String key){
        return new JDBCParameter<>(key, String.class);
    }

    public static <T> Parameter p(String key, Class<T> cls){
        return new JDBCParameter<>(key, cls);
    }

    public static And and(Directive left, Directive right){
        var re = new And();
        re._left = left;
        re._right = right;
        return re;
    }
    public static Or or(Directive left, Directive right){
        var re = new Or();
        re._left = left;
        re._right = right;
        return re;
    }
    public static Equal eq(Directive left, Directive right){
        var re = new Equal();
        re._left = left;
        re._right = right;
        return re;
    }
    public static Great gt(Directive left, Directive right){
        var re = new Great();
        re._left = left;
        re._right = right;
        return re;
    }
    public static Less ls(Directive left, Directive right){
        var re = new Less();
        re._left = left;
        re._right = right;
        return re;
    }
    public static GreateOrEqual gte(Directive left, Directive right){
        var re = new GreateOrEqual();
        re._left = left;
        re._right = right;
        return re;
    }

    public static LessOrEqual lte(Directive left, Directive right){
        var re = new LessOrEqual();
        re._left = left;
        re._right = right;
        return re;
    }
    public static NotEqual ne(Directive left, Directive right){
        var re = new NotEqual();
        re._left = left;
        re._right = right;
        return re;
    }

    public static Like like(Directive left, Directive right){
        var re = new Like();
        re._left = left;
        re._right = right;
        return re;
    }

    public static Func func(String name){
        return new Func(name);
    }

    public static Func func(String name, Directive arg){
        return new Func(name, arg);
    }

    public static Func func(String name, Directive... args){
        return new Func(name, args);
    }

}
