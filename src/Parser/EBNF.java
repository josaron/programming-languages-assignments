package Parser;

import javax.swing.tree.DefaultMutableTreeNode;

import tokenizer.TokConst;
import tokenizer.Token;

public class EBNF {
	
	public static ParseRule method_optional;
	
	public static ParseRule createGrammar() {
		// Create all the LHSs
		ParseRule program = new ParseRule("program", Globals.NONE);
		ParseRule class_ = new ParseRule("class", Globals.NONE);
		ParseRule class_optional = new ParseRule("class_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule classbody = new ParseRule("classbody", Globals.NONE);
		ParseRule method = new ParseRule("method", Globals.NONE);
		method_optional = new ParseRule("method_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule header = new ParseRule("header", Globals.NONE);
		ParseRule parmlist = new ParseRule("parmlist", Globals.OPTIONAL);
		ParseRule commaparmlist = new ParseRule("commaparmlist", Globals.OPTIONAL_REPETITIONS);
		ParseRule body = new ParseRule("bosy", Globals.NONE);
		ParseRule declaration = new ParseRule("declaration", Globals.NONE);
		ParseRule other_variable_optional = new ParseRule("other_variable_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule declaration_optional = new ParseRule("declaration_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule listofvariables = new ParseRule("listofvariables", Globals.NONE);
		ParseRule listofexpressions = new ParseRule("listofexpressions", Globals.NONE);
		ParseRule other_exp_optional = new ParseRule("other_exp_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule variable = new ParseRule("variable", Globals.NONE);
		ParseRule type = new ParseRule("type", Globals.NONE);
		ParseRule type_optional = new ParseRule("type_optional", Globals.ALTERNATES);
		ParseRule statement = new ParseRule("statement", Globals.ALTERNATES);
		ParseRule closed_statement = new ParseRule("closed_statement", Globals.NONE);
		ParseRule statement_optional = new ParseRule("statement_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule cin = new ParseRule("cin", Globals.NONE);
		ParseRule cout = new ParseRule("cout", Globals.NONE);
		ParseRule if_ = new ParseRule("if_", Globals.NONE);
		ParseRule else_optional = new ParseRule("else_optional", Globals.OPTIONAL);
		ParseRule exp = new ParseRule("exp", Globals.ALTERNATES);
		ParseRule e2 = new ParseRule("e2", Globals.NONE);
		ParseRule exp_paren = new ParseRule("exp_paren", Globals.NONE);
		ParseRule b_or_optional = new ParseRule("b_or_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule exp2 = new ParseRule("exp2", Globals.NONE);
		ParseRule b_and_optional = new ParseRule("b_and_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule exp3 = new ParseRule("exp3", Globals.NONE);
		ParseRule u_or_optional = new ParseRule("u_or_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule exp4 = new ParseRule("exp4", Globals.NONE);
		ParseRule u_and_optional = new ParseRule("u_and_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule exp5 = new ParseRule("exp5", Globals.NONE);
		ParseRule b_eq_optional = new ParseRule("b_eq_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule b_eq_alternates = new ParseRule("b_eq_alternates", Globals.ALTERNATES);
		ParseRule exp6 = new ParseRule("exp6", Globals.NONE);
		ParseRule than_optional = new ParseRule("than_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule than_alternates = new ParseRule("than_alternates", Globals.ALTERNATES);
		ParseRule exp7 = new ParseRule("exp7", Globals.NONE);
		ParseRule plusminus_optional = new ParseRule("plusminus_optional", Globals.NONE);
		ParseRule plusminus_alternates = new ParseRule("plusminus_alternates", Globals.NONE);
		ParseRule exp8 = new ParseRule("exp8", Globals.NONE);
		ParseRule multiply_optional = new ParseRule("multiply_optional", Globals.OPTIONAL_REPETITIONS);
		ParseRule multiply_alternates = new ParseRule("multiply_alternates", Globals.ALTERNATES);
		ParseRule exp9 = new ParseRule("exp9", Globals.NONE);
		ParseRule u_optional = new ParseRule("u_optional", Globals.OPTIONAL);
		ParseRule exp10 = new ParseRule("exp10", Globals.ALTERNATES);
		ParseRule assign = new ParseRule("assign", Globals.NONE);
		ParseRule while_ = new ParseRule("while_", Globals.NONE);
		ParseRule call = new ParseRule("call", Globals.NONE);
		ParseRule arglist = new ParseRule("arglist", Globals.OPTIONAL);
		
		
		// Variables for commonly used tokens
		Token COMMA = new Token(TokConst.CAMMA, ",");
		Token IDENT = new Token(TokConst.IDENT, "IDENT");
		Token LBRAC = new Token(TokConst.LBRAC, "{");
		Token RBRAC = new Token(TokConst.RBRAC, "}");
		Token LPAR = new Token(TokConst.LPAR, "(");
		Token RPAR = new Token(TokConst.RPAR, ")");
		Token SEMIC = new Token(TokConst.SEMIC, ";");
		
		
		
		
		
		// Create all the RHSs
		program.addRHS(class_, class_optional);
		class_.addRHS(new Token(TokConst.CLASS, "class"), IDENT, LBRAC, classbody, RBRAC);
		class_optional.addRHS(class_);
		classbody.addRHS(declaration_optional, method_optional);
		method.addRHS(header, LBRAC, body, RBRAC);
		method_optional.addRHS(method);
		header.addRHS(type, variable, LPAR, parmlist, RPAR);
		parmlist.addRHS(type, variable, commaparmlist);
		commaparmlist.addRHS(COMMA, type, variable);
		body.addRHS(declaration_optional, statement_optional);
		declaration.addRHS(type, variable, other_variable_optional, SEMIC);
		other_variable_optional.addRHS(COMMA, variable);
		declaration_optional.addRHS(declaration);
		listofvariables.addRHS(variable, other_variable_optional);
		listofexpressions.addRHS(exp, other_exp_optional);
		other_exp_optional.addRHS(COMMA, exp);
		variable.addRHS(new Token(TokConst.IDENT, "IDENT"));
		type.addRHS(type_optional);
		type_optional.addRHS(new Token(TokConst.INT, "int"), new Token(TokConst.CHAR, "char"), 
				new Token(TokConst.VOID, "void"));
		statement.addRHS(cin, cout, if_, assign, while_, call, closed_statement);
		closed_statement.addRHS(LBRAC, statement_optional, RBRAC);
		statement_optional.addRHS(statement);
		cin.addRHS(new Token(TokConst.CIN, "cin"), new Token(TokConst.RSHIFT, ">>"), 
				listofvariables, SEMIC);
		cout.addRHS(new Token(TokConst.COUT, "cout"), new Token(TokConst.LSHIFT, "<<"), 
				listofexpressions, SEMIC);
		if_.addRHS(new Token(TokConst.IF, "if"), LPAR, exp, RPAR, statement, else_optional);
		else_optional.addRHS(new Token(TokConst.ELSE, "else"), statement);
		exp.addRHS(e2, exp_paren);
		e2.addRHS(exp2, b_or_optional);
		exp_paren.addRHS(LPAR, exp, RPAR);
		b_or_optional.addRHS(new Token(TokConst.OROR, "||"), exp2);
		exp2.addRHS(exp3, b_and_optional);
		b_and_optional.addRHS(new Token(TokConst.ANDAND, "&&"), exp3);
		exp3.addRHS(exp4, u_or_optional);
		u_or_optional.addRHS(new Token(TokConst.ORB, "|"), exp4);
		exp4.addRHS(exp5, u_and_optional);
		u_and_optional.addRHS(new Token(TokConst.ANDB, "&"), exp5);
		exp5.addRHS(exp6, b_eq_optional);
		b_eq_optional.addRHS(b_eq_alternates, exp6);
		b_eq_alternates.addRHS(new Token(TokConst.EQUALEQUAL, "=="), 
				new Token(TokConst.NOTEQUAL, "!="));
		exp6.addRHS(exp7, than_optional);
		than_optional.addRHS(than_alternates, exp7);
		than_alternates.addRHS(new Token(TokConst.LESSEQUAL, "<="), new Token(TokConst.LESS, "<"), 
				new Token(TokConst.GREATER, ">"), new Token(TokConst.GREATEREQUAL, ">="));
		exp7.addRHS(exp8, plusminus_optional);
		plusminus_optional.addRHS(plusminus_alternates, exp8);
		plusminus_alternates.addRHS(new Token(TokConst.PLUS, "+"), new Token(TokConst.MINUS, "-"));
		exp8.addRHS(exp9, multiply_optional);
		multiply_optional.addRHS(multiply_alternates, exp9);
		multiply_alternates.addRHS(new Token(TokConst.STAR, "*"), new Token(TokConst.DIVIDE, "/"), 
				new Token(TokConst.MOD, "%"));
		exp9.addRHS(u_optional, exp10);
		u_optional.addRHS(new Token(TokConst.NOT, "!"), 
				new Token(TokConst.MINUS, "-"), new Token(TokConst.TILDE, "~"));
		exp10.addRHS(IDENT, new Token(TokConst.INTEGER, "INTEGER"), 
				new Token(TokConst.CONSTCHAR, "CONSTCHAR"));
		assign.addRHS(listofvariables, new Token(TokConst.EQUAL, "="), exp, SEMIC);
		while_.addRHS(new Token(TokConst.WHILE, "while"), LPAR, exp, RPAR, statement);;
		call.addRHS(variable, LPAR, arglist, RPAR);
		arglist.addRHS(listofexpressions);
		
		
		return program;
	}
}
