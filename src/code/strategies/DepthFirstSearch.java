package code.strategies;

import java.util.Queue;
import java.util.LinkedList;
import code.models.Node;
import code.interfaces.SearchStrategy;

public class DepthFirstSearch implements SearchStrategy {
    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        LinkedList<Node> newQueue = (LinkedList<Node>) nodes;
        for (Node node : newNodes) {
            newQueue.addFirst(node);
        }
        return newQueue;
    }
}