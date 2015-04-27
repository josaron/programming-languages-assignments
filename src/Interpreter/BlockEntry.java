package Interpreter;

import java.util.ArrayList;

public class BlockEntry {
	String name;
	int type;
	int blockNumber;
	int surroundBlockNumber;
	int totalMemory;
	ArrayList<SymbolEntry> symbols; //could be HashMap<String, SymbolEntry>
	
	public BlockEntry(String name, int type, int blockNumber, 
			int surroundBlockNumber, int totalMemory) {
		this.name = name;
		this.type = type;
		this.blockNumber = blockNumber;
		this.surroundBlockNumber = surroundBlockNumber;
		this.totalMemory = totalMemory;
	}
}
