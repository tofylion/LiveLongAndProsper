package code.models;

import java.util.LinkedList;
import java.util.List;

import code.treeprinter.SimpleTreeNode;
import code.enums.Actions;

public class Node implements Comparable<Node> {
    private Node parent;
    public State state;
    public Actions previousAction;
    public int depth;
    private SimpleTreeNode actionNode;
    private SimpleTreeNode simpleNode;

    public Node(Node parent, State state, Actions previousAction, int depth) {
        this.parent = parent;
        this.state = state;
        this.previousAction = previousAction;
        this.depth = depth;
        simpleNode = _toSimpleTreeNode();
    }

    public Node(State state) {
        parent = null;
        this.state = state;
        previousAction = null;
        depth = 0;
        simpleNode = _toSimpleTreeNode();
    }

    public String getPath() {

        List<String> planList = getPlan();
        String plan = "";
        int monetaryCost = state.getMoneySpent();
        for (int i = 0; i < planList.size() - 1; i++) {
            plan += " " + planList.get(i) + ",";
        }
        plan += " " + planList.get(planList.size() - 1);
        return plan + ";" + monetaryCost;

    }

    public List<String> getPlan() {
        List<String> accumulatedPlan = new LinkedList<>();

        Node currentNode = this;

        while (currentNode != null) {
            Actions previousAction = currentNode.previousAction;

            if (previousAction != null) {
                accumulatedPlan.add(0, previousAction.toString().toLowerCase());
            }

            currentNode = currentNode.parent;
        }

        return accumulatedPlan;
    }
    
    @Override
    public int compareTo(Node anotherNode) {
        // if this node's cost is less than the other one then return -1.
        // if it is more than the other one return 1.
        // if they are equal return 0 (can be replaced with a tiebreaker in the future).
        if (state.getMoneySpent() < anotherNode.state.getMoneySpent()) {
            return 1;
        } else if (state.getMoneySpent() > anotherNode.state.getMoneySpent()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object anotherNode) {
        if (anotherNode instanceof Node) {
            return (state.toString()).equals((((Node) anotherNode).state.toString()));
        }
        return false;
    }

    public Node nextNode(State nextState, Actions action) {
        return new Node(this, nextState, action, depth + 1);
    }

    private SimpleTreeNode _toSimpleTreeNode() {
        if (parent == null) {
            SimpleTreeNode simpleTreeNode = new SimpleTreeNode(state.toString());
            actionNode = simpleTreeNode;
            return simpleTreeNode;
        }
        SimpleTreeNode actionNode = new SimpleTreeNode(previousAction.toString());
        SimpleTreeNode stateNode = new SimpleTreeNode(state.toString());
        actionNode.addChild(stateNode);
        this.actionNode = actionNode;
        return stateNode;
    }

    public SimpleTreeNode toSimpleTreeNode() {
        return actionNode;
    }

    public void addSimpleTreeChildren(SimpleTreeNode[] children) {
        for (SimpleTreeNode child : children) {
            simpleNode.addChild(child);
        }
    }
}
