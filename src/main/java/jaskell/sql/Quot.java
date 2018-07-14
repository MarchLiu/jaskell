package jaskell.sql;

public class Quot extends Name {
    private final String _left;
    private final String _right;

    public Quot(String name) {
        super(name);
        _left = "\"";
        _right = "\"";
    }

    public Quot(String l, String name, String r){
        super(name);
        this._left = l;
        this._right = r;
    }

    public String left(){
        return _left;
    }

    public String right(){
        return _right;
    }

    @Override
    public String script() {
        return String.format("%s%s%s", this._left, super.script(), this._right);
    }
}
