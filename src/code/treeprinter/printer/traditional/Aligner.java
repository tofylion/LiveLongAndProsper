package code.treeprinter.printer.traditional;

import java.util.List;
import java.util.Map;

import code.treeprinter.TreeNode;

/**
 * Node aligner strategy interface for {@link TraditionalTreePrinter}
 */
public interface Aligner {
    
    public Placement alignNode(TreeNode node, int position, int width, int contentWidth);
    
    public int[] alignChildren(TreeNode parentNode, List<TreeNode> children, int position, Map<TreeNode, Integer> widthMap);
    
    public int collectWidths(Map<TreeNode, Integer> widthMap, TreeNode node);
    
}