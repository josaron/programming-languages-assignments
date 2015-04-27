package Interpreter;

public class SymbolEntry {

	public String name;
	public int type;
	public int blockNum;
	public int offset;
	
	public SymbolEntry(String name, int type, int blockNum, int offset) {
		this.name = name;
		this.type = type;
		this.blockNum = blockNum;
		this.offset = offset;
	}
	
}
