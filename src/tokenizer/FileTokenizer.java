package tokenizer;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

public class FileTokenizer extends JToken {
    PushbackInputStream fp;
    private boolean NoMoreData;

    public FileTokenizer() {
        super();
        NoMoreData = false;
        fp = new PushbackInputStream(System.in);
    }

    public FileTokenizer(String p) {
        super();
        NoMoreData = false;
        try {
            fp = new PushbackInputStream(new FileInputStream(p), 8);
        } catch (IOException e) {
            System.err.println("Can't open " + e.toString());
            System.exit(1);
        }
    }


    public char getChar() {
        int c = -1;
        try {
            c = fp.read();
        } catch (EOFException eof) {
            NoMoreData = true;
        } catch (IOException e) {
            c = -1;
        }
        //System.out.println(c);
        if (NoMoreData) c = -1;
        return (char) c;
    }

    public void ungetChar(char c) {
        if (c == (char) (-1)) return;
        try {
            fp.unread((int) c);
        } catch (IOException e) {
            System.err.println("Error " + e.toString());
            System.exit(1);
        }
    }

} //File_Tokenizer

