package tokenizer;

import org.omg.CORBA.ORB;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

abstract class JToken implements TokConst {
    private Token saved;
    private HashMap<String,Integer> reservedWords;
    private String[] reservedWordsArray;
    private int[][] digitStates;

    //array of tokens and size
    JToken() {
        saved = new Token();
        this.reservedWords = reservedWords();
        digitStates = new int[8][5];
        fillDigitStates();
    }

    private HashMap<String, Integer> reservedWords() {
        HashMap<String,Integer> reservedWords = new HashMap<String, Integer>();
        reservedWords.put("if", 1000);
        reservedWords.put("while", 1001);
        reservedWords.put("class", 1002);
        reservedWords.put("else", 1003);
        reservedWords.put("cin", 1004);
        reservedWords.put("cout", 1005);
        reservedWords.put("int", 1006);
        reservedWords.put("void", 1007);
        reservedWords.put("char", 1008);
        reservedWordsArray = Arrays.copyOf(reservedWords.keySet().toArray(), reservedWords.size(), String[].class);
        Arrays.sort(reservedWordsArray);
        return reservedWords;
    }


    void ungetToken(Token t) {
        saved = t;
    }

    protected abstract char getChar();

    public abstract void ungetChar(char c);


    public Token getToken() {

        char c;
        int i, tt, state;
        char tk[] = new char[1000];

        if (!saved.equals(new Token())) {
            Token t11 = saved;
            saved = new Token();
            return t11;
        }

        //skip spaces
        while (Character.isSpaceChar(c = getChar()) || c == '\r' || c == '\n'){

        }

        //if at end of input stream
        if (c == (char) (-1)) return new Token();

        i = 0;
        tk[i++] = c;


        //if an integer or float
        if (Character.isDigit(c) || c == '.') {
            if (c == '.') state = 4;
            else state = 2;
            c = getChar();
            int cVal;
            while (state > 0) {
                cVal = assignCVal(c);
                state = digitStates[state][cVal];
                if (state > 0) {
                    tk[i++] = c;
                    tk[i] = '\0';
                    c = getChar();
                }
            }
            ungetChar(c);
            switch (-state) {
                case 2:
                    return new Token(INTEGER, tk, i);
                case 3:
                case 6:
                    return new Token(FLOAT, tk, i);
                case 4:
                    break;
                default:
                    return new Token(BADFLOAT, tk, i);
            }
        }

        //identifier
        if (Character.isLetter(c) || c == '_') {
            while (Character.isLetterOrDigit(c = getChar()) || c == '_') {
                tk[i++] = c;
            }
            ungetChar(c);
            if (isReservedWords(String.copyValueOf(tk).trim())) {
                switch(String.copyValueOf(tk).trim()) {
                    case "if": return new Token(IF, tk, i);
                    case "while": return new Token(WHILE, tk, i);
                    case "class": return new Token(CLASS, tk, i);
                    case "else": return new Token(ELSE, tk, i);
                    case "cin": return new Token(CIN, tk, i);
                    case "cout": return new Token(COUT, tk, i);
                    case "int": return new Token(INT, tk, i);
                    case "void": return new Token(VOID, tk, i);
                    case "char": return new Token(CHAR, tk, i);
                }
            }else {
                return new Token(IDENT, tk, i);
            }
        }

        //OLDCOMMENT
        if (tk[0] == '/' && (c = getChar()) == '*') {
            tk[1] = '*';

            while ((c = getChar()) != '*' || (c = getChar()) != '/'){

            }
            return new Token(OLDCOMMENT, tk, i);
        }
        if (tk[0] == '/') return new Token(DIVIDE, tk, i);
        if (tk[0] == '*') return new Token(STAR, tk, i);
        if (tk[0] == '+') return new Token(PLUS, tk, i);
        if (tk[0] == '-') return new Token(MINUS, tk, i);
        if (tk[0] == '%') return new Token(MOD, tk, i);
        if (tk[0] == '(') return new Token(LPAR, tk, i);
        if (tk[0] == ')') return new Token(RPAR, tk, i);
        if (tk[0] == '{') return new Token(LBRAC, tk, i);
        if (tk[0] == '}') return new Token(RBRAC, tk, i);
        if (tk[0] == ';') return new Token(SEMIC, tk, i);
        if (tk[0] == ',') return new Token(CAMMA, tk, i);
        if (tk[0] == '~') return new Token(TILDE, tk, i);

        //LINECOMMENT
        if (tk[0] == '/' && (c = getChar()) == '/') {
            tk[1] = '/';

            while ((c = getChar()) != '\n') {
            }
            return new Token(LINECOMMENT, tk, i);
        }
        if (tk[0] == '/') ungetChar(c);

        //CONSTSTRING
        if (tk[0] == '"') {
            while ((c = getChar()) != '"' ||
                    (tk[i - 1] == '\\' && tk[i - 2] != '\\'))
                tk[i++] = c;
            tk[i++] = c;

            return new Token(CONSTSTRING, tk, i);
        }

        //CONSTCHAR
        if (tk[0] == '\'') {
            while ((c = getChar()) != '\'' ||
                    (tk[i - 1] == '\\' && tk[i - 2] != '\\'))
                tk[i++] = c;
            tk[i++] = c;

            return new Token(CONSTCHAR, tk, i);
        }

        if (tk[0] == '>') {
            if ((c = getChar()) == '=') {
                tk[i++] = c;
                return new Token(GREATEREQUAL, tk, i);
            }else if (c == '>') {
                tk[i++] = c;
                return new Token(RSHIFT, tk, i);
            }else {
                ungetChar(c);
                return new Token(GREATER, tk, i);
            }
        }

        if (tk[0] == '<') {
            if ((c = getChar()) == '=') {
                tk[i++] = c;
                return new Token(LESSEQUAL, tk, i);
            }else if (c == '>') {
                tk[i++] = c;
                return new Token(LSHIFT, tk, i);
            }else {
                ungetChar(c);
                return new Token(LESS, tk, i);
            }
        }

        if (tk[0] == '|') {
            if ((c = getChar()) == '|') {
                tk[i++] = c;
                return new Token(OROR, tk, i);
            }else{
                ungetChar(c);
                return new Token(ORB, tk, i);
            }
        }

        if (tk[0] == '&') {
            if ((c = getChar()) == '&') {
                tk[i++] = c;
                return new Token(ANDAND, tk, i);
            }else{
                ungetChar(c);
                return new Token(ANDB, tk, i);
            }
        }

        if (tk[0] == '=') {
            if ((c=getChar())== '='){
                tk[i++] = c;
                return new Token(EQUALEQUAL, tk, i);
            }else {
                ungetChar(c);
                return new Token(EQUAL, tk, i);
            }
        }
        if (tk[0] == '!') {
            if ((c = getChar()) == '=') {
                tk[i++] = c;
                return new Token(NOTEQUAL, tk, i);
            }
            else {
                ungetChar(c);
                return new Token(NOT, tk, i);
            }
        }
        return new Token();
    }

    private boolean isReservedWords(String s) {
        int low = 0;
        int high = reservedWords.size() - 1;
        while(high >= low) {
            int middle = (low + high) / 2;
            if(reservedWordsArray[middle].equals(s)) {
                return true;
               }
            if(reservedWordsArray[middle].compareTo(s) <= -1) {
                low = middle + 1;
                }
            if(reservedWordsArray[middle].compareTo(s) >= 1) {
                high = middle - 1;
                }
          }
        return false;
    }

    private void fillDigitStates() {
        digitStates[2][0] = 2;
        digitStates[2][1] = 5;
        digitStates[2][2] = 3;
        digitStates[2][4] = -2;

        digitStates[3][0] = 3;
        digitStates[3][1] = 5;
        digitStates[3][4] = -3;

        digitStates[4][0] = 3;
        digitStates[4][4] = -4;

        digitStates[5][0] = 6;
        digitStates[5][3] = 6;
        digitStates[5][4] = -5;

        digitStates[6][0] = 6;
        digitStates[6][4] = -6;

        digitStates[7][0] = 6;
        digitStates[7][4] = -7;
    }

    private int assignCVal(char c) {
        if (Character.isDigit(c)) {
            return 0;
        }
        else if (c == 'e' || c == 'E') {
            return 1;
        }
        else if (c == '.') {
            return 2;
        }
        else if (c == '+' || c == '-') {
            return 3;
        }
        else {
            return 4;
        }
    }
}