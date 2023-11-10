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
        LinkedList<Node> newQueue = new LinkedList<>();
        for (int i = newNodes.length - 1; i >= 0; i--) {
            newQueue.addFirst(newNodes[i]);
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