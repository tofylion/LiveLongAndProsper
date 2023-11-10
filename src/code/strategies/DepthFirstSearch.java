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
        Stack<Node> helpingStack = new Stack<Node>();
        Queue<Node> queue = new LinkedList<>();

        for (int i = newNodes.length - 1; i >= 0; i--) {
            stack.push(newNodes[i]);
        }

        for (Node node : nodes) {
            helpingStack.push(node);
        }

        while (true) {
            try {
                helpingStack.peek();
                stack.push(helpingStack.pop());
            } catch (Exception e) {
                break;
            }
        }
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        
        return queue;
    }
}