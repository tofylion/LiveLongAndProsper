package code.strategies;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import code.models.Node;
import code.models.NodeByBuild;
import code.interfaces.SearchStrategy;

public class GreedySearch implements SearchStrategy  {

    public Comparator<Node> firstHeuristic;
    public Comparator<Node> secondHeuristic;

    public GreedySearch(Comparator<Node> firstHeuristic, Comparator<Node> secondHeuristic){
        this.firstHeuristic = firstHeuristic;
        this.secondHeuristic = secondHeuristic;
    }

    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(nodes.size() + newNodes.length, firstHeuristic);

        //add the existing nodes to the PQ
        for (Node node: nodes){
            queue.add(node);
        }
        //add the new nodes to the PQ
        for (Node node: newNodes){
            queue.add(node);
        }
        //typecast the PQ to a normal queue
        Queue<Node> returnQueue = queue;
        return returnQueue;

    }
}

