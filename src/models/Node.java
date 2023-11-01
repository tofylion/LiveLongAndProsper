package models;
import models.State;

public class Node {
    private double monetaryCost;
    private Node parent;
    private State state;
    private String previousAction;

    public Node(double monetaryCost, int nodesExpanded, Node parent, State state, String previousAction) {
       
        this.monetaryCost = monetaryCost;
        this.nodesExpanded = nodesExpanded;
        this.parent = parent;
        this.state = state;
        this.previousAction = previousAction;
    }

    public String getPath() {
        
        List<String> plan = getPlan();
        monetaryCost = getState.getMoneySpent();
        int nodesExpanded = getNodesExpaned(this);
        return String.join(plan.join(",") + ";" + monetaryCost);
        
    }

    public List<String> getPlan() {
        List<String> accumulatedPlan = new LinkedList<>();
    
        Node currentNode = this;
    
        while (currentNode != null) {
            String previousAction = currentNode.getState().getPreviousAction();
            
            if (previousAction != null) {
                accumulatedPlan.add(0, previousAction);
            }
    
            currentNode = currentNode.getParent();
        }
    
        return accumulatedPlan;
    }

    public double getMoneySpent()
    {
        double moneySpent = 0.0;
        Node currentNode = this;
        moneySpent = currentNode.getState().getMoneySpent();
        
        return moneySpent;
    }

    
        
        
    }

