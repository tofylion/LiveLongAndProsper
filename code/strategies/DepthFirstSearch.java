package code.strategies;

import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import code.models.Node;
import code.interfaces.SearchStrategy;

public class DepthFirstSearch implements SearchStrategy {
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
}