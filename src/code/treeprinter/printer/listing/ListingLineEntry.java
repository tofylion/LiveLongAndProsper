package code.treeprinter.printer.listing;

import code.treeprinter.TreeNode;
import code.treeprinter.text.ConsoleText;

public class ListingLineEntry {

    private final TreeNode node;

    private final ConsoleText liningPrefix;

    private final ConsoleText contentLine;
    
    private final boolean hasMoreForThisNode;

    public ListingLineEntry(
            TreeNode node, ConsoleText liningPrefix, ConsoleText contentLine, boolean hasMoreForThisNode) {
        this.node = node;
        this.liningPrefix = liningPrefix;
        this.contentLine = contentLine;
        this.hasMoreForThisNode = hasMoreForThisNode;
    }

    public TreeNode node() {
        return node;
    }

    public ConsoleText liningPrefix() {
        return liningPrefix;
    }

    public ConsoleText contentLine() {
        return contentLine;
    }

    public boolean hasMoreForThisNode() {
        return hasMoreForThisNode;
    }
    
}
