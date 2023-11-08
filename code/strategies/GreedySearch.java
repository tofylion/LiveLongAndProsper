package code.strategies;

import java.util.PriorityQueue;
import java.util.Queue;

import code.interfaces.SearchStrategy;
import code.models.Node;
import code.models.HeuristicBluePrint;

public class GreedySearch implements SearchStrategy{

    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(nodes.size() + newNodes.length, new HeuristicBluePrint());
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
