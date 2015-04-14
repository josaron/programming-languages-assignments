package tokenizer;

import javax.swing.*;
import javax.swing.tree.*;
import java.util.LinkedList;

public class TokenizerTest {

	public static void main(String[] args) {
        Token token;
        LinkedList<Token> tokens = new LinkedList<Token>();
        FileTokenizer fileTokenizer = new FileTokenizer("testfile.txt");
        //while (!((token = fileTokenizer.getToken()).equals(new Token()))){
        while ((token = fileTokenizer.getToken()).getType() > 0){
            tokens.add(token);
            System.out.println("Token: " + token + " type: " + token.type + token.ap);
        }
	}
}


/*import javax.swing.*;
import javax.swing.tree.*;
import java.util.LinkedList;

public class ParserTest extends JFrame {

    public static void main(String[] args) {
        Token token;
        LinkedList<Token> tokens = new LinkedList<Token>();
        FileTokenizer fileTokenizer = new FileTokenizer("testfile.txt");
        while (!((token = fileTokenizer.getToken()).equals(new Token()))){
            tokens.add(token);
            System.out.println("Token: " + token + " type: " + token.type);
        }
        Parser parser = new Parser(tokens);
        
        DefaultMutableTreeNode root = parser.parse();
        TreeFrame tree = new TreeFrame(root);
        tree.pack();
    }
}
*/