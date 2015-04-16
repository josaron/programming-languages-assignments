package Parser;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.*;

public class ParseRule implements LeftHandSide {

	protected LeftHandSide[] rules;
	private String lhsName;
	private int property; // specifies the Ebnf abbreviation
	private HashMap<String, Integer> symbolTable;
	private int tableSpot;
	
	public ParseRule(String name, int property) {
		lhsName = "<" + name + ">";
		this.property = property;
		symbolTable = new HashMap<String, Integer>();
		tableSpot = -1;
	}
	
	public void addRHS(LeftHandSide... rhs) {
		rules = rhs;
	}
	
	public boolean parse(DefaultMutableTreeNode parent) {
		System.out.println("               lhs: " + lhsName);
		for (LeftHandSide rhs : rules) {
			if (isVar(rhs)) { //the RHS is a variable
				ParseRule rule = (ParseRule) rhs;
				if (rule.getProperty() == Globals.NONE) {
					System.out.println(lhsName + " -> " + rule.lhsName);
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
					parent.add(n);
					if (!rule.parse(n)) return false;
				}
				else if (rule.getProperty() == Globals.OPTIONAL) {
					Token nextTerminalRule = getNextTerminal(rule);
					if (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						System.out.println(lhsName + " -> " + rule.getName());
						if (!rule.parse(n)) return false;
					}
				}
				else if (rule.getProperty() == Globals.OPTIONAL_REPETITIONS) {
					Token nextTerminalRule = getNextTerminal(rule);
					while (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						if (isMethodOptional(rule)) rule = EBNF.method_optional; // Account for pairwise disjointness is the flawed grammar. This line and helper method should not be here!
						//System.out.println(Globals.currentToken);
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						System.out.println(lhsName + " -> " + rule.getName());
						if (!rule.parse(n)) return false;
					}
				}
				else if (rule.getProperty() == Globals.ALTERNATES) {
					for (LeftHandSide option : rule.rules) {
						if (option.getClass() != this.getClass()) {
							Token t = (Token) option;
							if (t.getType() == Globals.currentToken.getType()) {
								DefaultMutableTreeNode n = new DefaultMutableTreeNode(t);
								parent.add(n);
								Globals.currentToken = Globals.tokenizer.getToken();
								System.out.println(lhsName + " -> " + t.toString());
								return true;
							}
						}
						else {
							ParseRule pr = (ParseRule) option;
							Token t = getNextTerminal(pr);
							if (t.getType() == Globals.currentToken.getType()) {
								DefaultMutableTreeNode n = new DefaultMutableTreeNode(pr.getName());
								parent.add(n);
								return pr.parse(n);
							}
						}
					}
					return false;
				}
			}
			
			else { // the LHS is a terminal
				Token terminalRule = (Token) rhs;
				Token tokenTerminal = (Token) Globals.currentToken;
				if (tokenTerminal.getType() == terminalRule.getType()) {
					System.out.println(lhsName + " -> " + tokenTerminal.toString());
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(tokenTerminal);
					parent.add(n);
					if (terminalRule.getType() == TokConst.IDENT) {
						addSymbol(terminalRule);
					}
					Globals.currentToken = Globals.tokenizer.getToken();
				}
				else {
					return false;
				}
			}
		}
		return true;
	}
	
	private Token getNextTerminal(LeftHandSide rhs) {
		if (!isVar(rhs)) {
			return (Token) rhs;
		}
		else {
			ParseRule rule = (ParseRule) rhs;
			return getNextTerminal(rule.rules[0]);
		}
	}
	
	public String getName() {
		return lhsName;
	}
	
	public int getProperty() {
		return property;
	}
	
	private boolean isVar(LeftHandSide rule) {
		return rule.getClass() == this.getClass();
	}
	
	private void addSymbol(Token t) {
		if (symbolTable.containsKey(t.toString())) {
			symbolTable.put(t.toString(), tableSpot++);
		}
	}
	
	private boolean isMethodOptional(LeftHandSide rhs) {
		if (isVar(rhs)) {
			ParseRule rule = (ParseRule) rhs;
			if (rule.lhsName.equals("<declaration_optional>")) {
				Token t1 = Globals.tokenizer.getToken();
				Token t2 = Globals.tokenizer.getToken();
				Globals.tokenizer.ungetToken(t2);
				Globals.tokenizer.ungetToken(t1);
				Token t3 = Globals.tokenizer.getToken();
				
				Token t4 = Globals.tokenizer.getToken();
				//Globals.currentToken = t4;
				/*Token t5 = Globals.tokenizer.getToken();
				Token t6 = Globals.tokenizer.getToken();
				Token t7 = Globals.tokenizer.getToken();
				Token t8 = Globals.tokenizer.getToken();*/
				if (t2.getType() == TokConst.LPAR) {
					return true;
				}
			}
			else {
				rule = (ParseRule) rhs;
				return isMethodOptional(rule.rules[0]);
			}
		}
		return false;
	}
}
