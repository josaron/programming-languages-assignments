package Parser;

import javax.swing.*;
import javax.swing.tree.*;

public class TreeFrame extends JFrame {

    public TreeFrame(DefaultMutableTreeNode root) {
        JTree tree = new JTree(root);
        add(new JScrollPane(tree));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        setTitle("Parse Tree");
        setVisible(true);

        for (int i = 0; i < tree.getRowCount(); i++) tree.expandRow(i);
    }
}
