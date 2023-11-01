package strategies;

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import models.Node;
import interfaces.SearchStrategy;

public class IterativeDeepeningSearch implements SearchStrategy {

    private int maxDepth;

    public IterativeDeepeningSearch(int maxDepth){
        this.maxDepth = maxDepth;
    }

    @Override
    public Queue<Node> queueingFunction(Queue<Node> nodes, Node[] newNodes) {
        Stack<Node> stack = new Stack<>();
        Queue<Node> queue = new LinkedList<>();

        for (int i = newNodes.length - 1; i >= 0; i--) {
            stack.push(newNodes[i]);
        }

        for (Node node : nodes) {
            stack.push(node);
        }

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        
        return queue;
    }
    public int getMaxDepth(){
        return maxDepth;
    }
}