package jaskell.sql;

import jaskell.script.Directive;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Join implements Directive {
    Directive _prefix;
    Directive _join;

    public On on(Predicate _on){
        var re = new On();
        re._join = this;
        re._on.add(_on);
        return re;
    }

    @Override
    public String script() {
        return String.format("%s join %s", _prefix, _join);
    }

    @Override
    public List<jaskell.script.Parameter> parameters() {
        var re = _prefix.parameters();
        re.addAll(_join.parameters());
        return re;
    }

    public static class On extends Query {
        Join _join;
        List<Predicate> _on = new ArrayList<>();

        public On on(Predicate _on){
            this._on.add(_on);
            return this;
        }

        public Join join(Directive other){
            var re = new Join();
            re._prefix = this;
            re._join = other;
            return re;
        }

        @Override
        public String script() {
            return String.format("%s on %s", _join.script(),
                    _on.stream().map(Directive::script).collect(Collectors.joining(", ")));
        }

        @Override
        public List<jaskell.script.Parameter> parameters() {
            var re = _join.parameters();
            _on.forEach(item->re.addAll(item.parameters()));
            return re;
        }

        public Group group() {
            var re = new Group();
            re._prefix = this;
            return re;
        }

        public Order order() {
            var re = new Order();
            re._prefix = this;
            return re;
        }
    }
}

