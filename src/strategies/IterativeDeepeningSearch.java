package strategies;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import models.Node;
import interfaces.SearchStrategy;

public class IterativeDeepeningSearch implements SearchStrategy {

    private int maxDepth; // Maximum depth to explore
    private DepthFirstSearch dfs; // Depth First Search instance

    public IterativeDeepeningSearch(int maxDepth) {
        this.maxDepth = maxDepth;
        this.dfs = new DepthFirstSearch();
    }

    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        // Perform depth-limited search with increasing depth limits
        for (int depth = 0; depth <= maxDepth; depth++) {
            Queue<Node> result = performDepthLimitedSearch(nodes, newNodes, depth);
            if (result != null) {
                return result;
            }
        }

        // Return an empty LinkedList if goal is not found within the depth limit
        return new LinkedList<>(); 
    }

    private Queue<Node> performDepthLimitedSearch(Queue<Node> nodes, Node[] newNodes, int depthLimit) {
    Queue<Node> combinedNodes = new LinkedList<>();
    combinedNodes.addAll(nodes);
    
    // Create a list of the new nodes explored for the new depth limit
    List<Node> newNodesList = new ArrayList<>();
    Collections.addAll(newNodesList, newNodes);

    // Add the new nodes to the combined nodes list
    combinedNodes.addAll(newNodesList);

    // Call the DepthFirstSearch queueing function on the nodes within the depth limit
    return dfs.queueingFunction(combinedNodes, new Node[0]);
}

}
