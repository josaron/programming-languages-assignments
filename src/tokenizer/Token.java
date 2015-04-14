package tokenizer;

public class Token implements LeftHandSide {
    int type;
    String ap;

    public Token() {
        type = -1;
        ap = new String("");
    }

    public Token(int t, String v) {
        type = t;
        ap = new String(v);
    }

    public Token(char t) {
        char[] v = new char[1];
        v[0] = t;
        type = t;
        ap = new String(v, 0, 1);
    }

    public Token(int t, char[] v, int j) {
        type = t;
        ap = new String(v, 0, j);
    }

    public Token(Token t) {
        type = t.type;
        ap = new String(t.ap);
    }

    public String toString() {
        return ap;
    }

    boolean equals(Token t) {
        return type == t.type && ap.equals(t.ap);
    }

    public int getType() {
        return type;
    }

    String getValue() {
        return ap;
    }
}


