package code.strategies;

import java.util.Queue;
import code.models.Node;
import code.interfaces.SearchStrategy;

public class BFS implements SearchStrategy {
    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {

        for (int i = 0; i <= newNodes.length - 1; i++) {
            nodes.add(newNodes[i]);
        }
        
        return nodes;
    }
}