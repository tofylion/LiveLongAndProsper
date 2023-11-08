package treeprinter.decorator;

import java.util.ArrayList;
import java.util.List;

import treeprinter.Insets;
import treeprinter.TreeNode;
import treeprinter.text.ConsoleText;

/**
 * Base class for {@link TreeNode} decorators.
 * 
 * Decorators should only effect if the wrapped node is decorable.
 * Decorators should inherit by default (wrap their children with a similar decorator),
 * and should not inherit when the <code>inherit</code> option is set to <code>false</code>.
 */
public abstract class AbstractTreeNodeDecorator implements TreeNode {
    
    protected final TreeNode baseNode;
    
    protected final boolean inherit;

    protected final boolean decorable;
    

    protected AbstractTreeNodeDecorator(TreeNode baseNode) {
        this(baseNode, true);
    }

    protected AbstractTreeNodeDecorator(TreeNode baseNode, boolean inherit) {
        this(baseNode, inherit, baseNode.isDecorable());
    }

    protected AbstractTreeNodeDecorator(TreeNode baseNode, boolean inherit, boolean decorable) {
        if (baseNode == null) {
            throw new IllegalArgumentException("Decorated node must not be null");
        }
        
        this.baseNode = baseNode;
        this.inherit = inherit;
        this.decorable = decorable;
    }
    

    @Override
    public ConsoleText content() {
        if (baseNode.isDecorable()) {
            return decoratedContent();
        } else {
            return baseNode.content();
        }
    }
    
    public TreeNode getDecoratedNode() {
        return baseNode;
    }

    @Override
    public TreeNode originalNode() {
        return baseNode.originalNode();
    }
    
    @Override
    public Insets insets() {
        return baseNode.insets();
    }
    
    @Override
    public boolean isDecorable() {
        return decorable;
    }

    @Override
    public boolean isPlaceholder() {
        return baseNode.isPlaceholder();
    }

    @Override
    public List<TreeNode> children() {
        List<TreeNode> wrappedChildren = new ArrayList<>();
        List<TreeNode> originalChildren = baseNode.children();
        int childCount = originalChildren.size();
        for (int i = 0; i < childCount; i++) {
            TreeNode childNode = originalChildren.get(i);
            TreeNode wrappedChildNode = inherit ? wrapChild(childNode, i) : childNode;
            wrappedChildren.add(wrappedChildNode);
        }
        return wrappedChildren;
    }
    
    protected abstract ConsoleText decoratedContent();

    protected abstract TreeNode wrapChild(TreeNode childNode, int index);

}
