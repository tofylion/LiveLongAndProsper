package code.strategies;

import java.util.PriorityQueue;
import java.util.Queue;
import code.interfaces.SearchStrategy;
import code.models.Node;

public class UniformCost implements SearchStrategy {

    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes){
        //create a priority queue of length that fits both entries
        PriorityQueue<Node> queue = new PriorityQueue<Node>(nodes.size() + newNodes.length);
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