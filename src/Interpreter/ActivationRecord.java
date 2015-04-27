package Interpreter;

import javax.swing.tree.DefaultMutableTreeNode;

public class ActivationRecord {
	DefaultMutableTreeNode returnAddress;
	int staticLink;
	int dynamicLink;
	//int parameterValues[];
	int localVars[];
	int returnValue;
}
