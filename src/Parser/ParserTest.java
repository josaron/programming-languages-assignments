package Parser;

import java.util.LinkedList;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.FileTokenizer;
import tokenizer.TokConst;
import tokenizer.Token;

public class ParserTest {
	
	public static void main(String[] args) {
		Globals.tokenizer = new FileTokenizer("testfile.txt");
		Globals.currentToken = Globals.tokenizer.getToken();
		
		// specify rules
		
		// Test EBNF
		/*ParseRule program = new ParseRule("program", Globals.NONE);
		ParseRule clas = new ParseRule("class", Globals.NONE);
		ParseRule field = new ParseRule("field", Globals.NONE);
		ParseRule method = new ParseRule("method", Globals.OPTIONAL_REPETITIONS);
		ParseRule alternates = new ParseRule("alternates", Globals.ALTERNATES);
		method.addRHS(new Token(TokConst.INTEGER, "INTEGER"), new Token(TokConst.PLUS, "+"), 
				new Token(TokConst.IDENT, "IDENT"), new Token(TokConst.SEMIC, ";"));
		field.addRHS(new Token(TokConst.IDENT, "IDENT"), new Token(TokConst.EQUAL, "="), 
				new Token(TokConst.INTEGER, "INTEGER"), new Token(TokConst.SEMIC, ";"));
		//clas.addRHS(field, method, alternates);
		clas.addRHS(alternates);
		program.addRHS(clas);
		
		//alternates.addRHS(new Token(TokConst.PLUS, "+"), new Token(TokConst.MINUS, "-"));
		alternates.addRHS(method, field);*/
		
		ParseRule start = EBNF.createGrammar();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(start.getName());
		
		boolean successfulParse = start.parse(root) && Globals.currentToken.toString().equals("");
		System.out.println("Successful parse -> " + successfulParse);
		TreeFrame tree = new TreeFrame(root);
		tree.pack();
	}
}
