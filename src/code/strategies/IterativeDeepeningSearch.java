package code.strategies;

import java.util.Queue;
import java.util.LinkedList;
import code.models.Node;
import code.interfaces.SearchStrategy;

public class IterativeDeepeningSearch implements SearchStrategy {

    private int maxDepth;

    public IterativeDeepeningSearch(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        Queue<Node> newQueue = new LinkedList<Node>();
        for (Node node : newNodes) {
            newQueue.add(node);
        }
        for (Node node : nodes) {
            newQueue.add(node);
        }
        return newQueue;

    }

    public int getMaxDepth() {
        return maxDepth;
    }
}