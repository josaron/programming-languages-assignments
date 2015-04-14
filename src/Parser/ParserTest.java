package Parser;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.FileTokenizer;
import tokenizer.TokConst;
import tokenizer.Token;

public class ParserTest {
	
	public static void main(String[] args) {
		Globals.tokenizer = new FileTokenizer("testfile1.txt");
		Globals.currentToken = Globals.tokenizer.getToken();
		
		// specify rules
		// Test EBNF
		ParseRule program = new ParseRule("program", Globals.NONE, 1);
		ParseRule clas = new ParseRule("class", Globals.NONE, 1);
		ParseRule field = new ParseRule("field", Globals.NONE, 1);
		ParseRule method = new ParseRule("method", Globals.OPTIONAL_REPETITIONS, 1);
		method.addRHS(new Token(TokConst.INTEGER, "INTEGER"), new Token(TokConst.PLUS, "+"), 
				new Token(TokConst.IDENT, "IDENT"), new Token(TokConst.SEMIC, ";"));
		field.addRHS(new Token(TokConst.IDENT, "IDENT"), new Token(TokConst.EQUAL, "="), 
				new Token(TokConst.INTEGER, "INTEGER"), new Token(TokConst.SEMIC, ";"));
		clas.addRHS(field, method);
		program.addRHS(clas);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(program.getName());
		boolean successfulParse = program.parse(root) && Globals.currentToken.toString().equals("");
		System.out.println("Successful parse -> " + successfulParse);
		TreeFrame tree = new TreeFrame(root);
		tree.pack();
	}
}
