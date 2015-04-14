package Parser;

import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.*;

public class ParseRule implements LeftHandSide {

	protected LeftHandSide[] rules;
	private int ruleCount;
	private String lhsName;
	private int extendedOption;
	
	public ParseRule(String name, int extendedOption, int i) {
		lhsName = name;
		this.extendedOption = extendedOption;
		ruleCount = 0;
	}
	
	public void addRHS(LeftHandSide... rhs) {
		rules = rhs;
		ruleCount++;
	}
	
	public boolean parse(DefaultMutableTreeNode parent) {
		//LeftHandSide[] rules = findChildRule();
		/*if (ruleOptions.length > 1) {
			System.out.println("Error finding which rule");
			return false;
		}*/
		
		//for (int i = 0; i < rules.length; i++) {
			//LeftHandSide rhs = rules[i];
		for (LeftHandSide rhs : rules) {
			
			if (rhs.getClass() == this.getClass()) { //the RHS is a variable
				ParseRule rule = (ParseRule) rhs;
				Token nextTerminalRule = getNextTerminal(rule);
				
				if (rule.getExtendedOption() == Globals.NONE) {
					//System.out.println(lhsName);
					DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
					parent.add(n);
					if (!rule.parse(n)) return false;
				}
				else if (rule.getExtendedOption() == Globals.OPTIONAL) {
					if (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						if (!rule.parse(n)) return false;
					}
				}
				else if (rule.getExtendedOption() == Globals.OPTIONAL_REPETITIONS) {
					while (Globals.currentToken.getType() == nextTerminalRule.getType()) {
						DefaultMutableTreeNode n = new DefaultMutableTreeNode(rule.getName());
						parent.add(n);
						if (!rule.parse(n)) return false;
					}
				}
				else if (rule.getExtendedOption() == Globals.ALTERNATIVES) {
					if (!rule.parse(parent)) return false;
				}
			}
			
			/*else if (rhs.getType() == TokConst.LBRAC_OPTIONAL) { //optional is a possibility
				rhs = rules[i++];
				if (GlobalTokenizer.currentToken == nextTerminalRule) { //enter the part of optional
					//ParseRule nextRule = GlobalTokenizer.rules.get(rules[i++]);
					do {
						rhs.parse(parent);
						rhs = rules[i++];
					} while (rhs.getType() != TokConst.RBRAC_OPTIONAL);
				}
			}
			else if (rhs.getType() == TokConst.RCURLY_OPTIONAL) { //zero or more repetitions is a possibility
				while (GlobalTokenizer.currentToken == nextTerminalRule) { //enter a repetition
					parse(parent);
				}
			}*/
			
			else { // the LHS is a terminal
				Token terminalRule = (Token) rhs;
				Token tokenTerminal = (Token) Globals.currentToken;
				if (tokenTerminal.getType() == terminalRule.getType()) {
					//System.out.println(tokenTerminal.toString());
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
		if (rhs.getClass() != this.getClass()) {
			return (Token) rhs;
		}
		else {
			ParseRule rule = (ParseRule) rhs;
			return getNextTerminal(rule.rules[0]);
		}
	}
	
	/*private LeftHandSide[] findChildRule() {
		if (ruleOptions.length == 1) {
			return ruleOptions[0];
		}//
		/*for (int i = 0; i < ruleOptions.length; i++) {
			if (ruleOptions[i].getValidStartTerminal()) {
				return ruleOptions[i];
			}
		}*/
		/*return new LeftHandSide[0];
	}*/
	
	public String getName() {
		return lhsName;
	}
	
	public int getExtendedOption() {
		return extendedOption;
	}
}
