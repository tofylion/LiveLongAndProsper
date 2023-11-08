package treeprinter.decorator;

import treeprinter.TreeNode;
import treeprinter.text.ConsoleText;

/**
 * Helper decorator implementation of {@link TreeNode} that tracks its position in the tree
 */
public class TrackingTreeNodeDecorator extends AbstractTreeNodeDecorator {

    public final TrackingTreeNodeDecorator parent;
    
    public final int index;
    

    public TrackingTreeNodeDecorator(TreeNode baseNode) {
        this(baseNode, null, 0);
    }
    
    public TrackingTreeNodeDecorator(TreeNode baseNode, TrackingTreeNodeDecorator parent, int index) {
        super(baseNode);
        this.parent = parent;
        this.index = index;
    }

    
    @Override
    public ConsoleText decoratedContent() {
        return baseNode.content();
    }

    @Override
    protected TreeNode wrapChild(TreeNode childNode, int index) {
        return new TrackingTreeNodeDecorator(childNode, this, index);
    }

    @Override
    public boolean isDecorable() {
        return false;
    }
    
    @Override
    public int hashCode() {
        int parentHashCode = parent != null ? parent.hashCode(): 0;
        return (parentHashCode * 37) + index;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TrackingTreeNodeDecorator)) {
            return false;
        }

        TrackingTreeNodeDecorator otherTrackingTreeNodeDecorator = (TrackingTreeNodeDecorator) other;
        TrackingTreeNodeDecorator otherParent = otherTrackingTreeNodeDecorator.parent;
        
        if (this == otherTrackingTreeNodeDecorator) {
            return true;
        } else if (parent == null) {
            if (otherParent != null) {
                return false;
            }
        } else if (otherParent == null || !parent.equals(otherParent)) {
            return false;
        }
        
        return index == otherTrackingTreeNodeDecorator.index;
    }
    
}
