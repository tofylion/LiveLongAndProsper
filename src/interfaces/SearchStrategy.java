package interfaces;

import java.util.Queue;

import models.Node;

public interface SearchStrategy {
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes);
}
