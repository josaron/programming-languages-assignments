package Parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import tokenizer.FileTokenizer;
import tokenizer.Token;

public class Globals {

	public static FileTokenizer tokenizer;
	public static Token currentToken;
	public static ArrayList<ParseRule> rules;

	public static final int NONE = 0;
	public static final int OPTIONAL = 1;
	public static final int OPTIONAL_REPETITIONS = 2;
	public static final int ALTERNATIVES = 3;
}
