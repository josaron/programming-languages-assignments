package Parser;

import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.*;

public class ParseRule implements LeftHandSide {

	protected LeftHandSide[] rules;
	private String lhsName;
	private int property; // specifies the Ebnf abbreviation
	
	public ParseRule(String name, int property) {
		lhsName = name;
		this.property = property;
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
					System.out.println(lhsName);
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
					parent.add(n);
					if (!rule.parse(n)) return false;
				}
				else if (rule.getProperty() == Globals.OPTIONAL) {
					Token nextTerminalRule = getNextTerminal(rule);
					if (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						System.out.println(rule.getName());
						if (!rule.parse(n)) return false;
					}
				}
				else if (rule.getProperty() == Globals.OPTIONAL_REPETITIONS) {
					Token nextTerminalRule = getNextTerminal(rule);
					while (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						System.out.println(rule.getName());
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
								System.out.println(t.toString());
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
					System.out.println(tokenTerminal.toString());
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(tokenTerminal);
					parent.add(n);
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
}
