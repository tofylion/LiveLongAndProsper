package strategies;

import java.util.PriorityQueue;
import java.util.Queue;
import interfaces.SearchStrategy;
import models.Node;

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

    //main method for testing purposes
    public static void main(String[] args){
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);

        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(n4);
        queue.add(n1);
        queue.add(n2);
        Node[] nodes = {n5, n3};
        UniformCost ucs = new UniformCost();
        Queue<Node> castedQueue = queue;
        Queue<Node> finalQueue = ucs.queueingFunction(castedQueue, nodes);
        
        for (int i = 0; i < 5; i++){
            System.out.println(finalQueue.remove().cost);
        }
    }

}