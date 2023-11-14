package code.interfaces;

import java.util.Queue;

import code.models.Node;

public interface SearchStrategy {
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes);
}
