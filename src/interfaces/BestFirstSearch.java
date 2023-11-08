package interfaces;

import java.util.Queue;

import models.Node;

public interface BestFirstSearch extends SearchStrategy{
    public Queue<Node> firstHeuristic(Queue<Node> nodes, Node[] newNodes);
    public Queue<Node> secondHeuristic(Queue<Node> nodes, Node[] newNodes);

}
