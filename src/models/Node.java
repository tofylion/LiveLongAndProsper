package models;
import models.State;

public class Node {
    private double monetaryCost;
    private int nodesExpanded;
    private Node parent;
    private State state;

    public Node(double monetaryCost, int nodesExpanded, Node parent, State state) {
       
        this.monetaryCost = monetaryCost;
        this.nodesExpanded = nodesExpanded;
        this.parent = parent;
        this.state = state;
    }

    public String getPath() {
        
        List<String> plan = getPlan();
        double monetaryCost = getMoneySpent();
        int nodesExpanded = getNodesExpaned();
        return String.join(plan + "," + monetaryCost + "," + nodesExpanded)
        
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
        moneySpend = currentNode.getState().getMoneySpent();
        
        return moneySpent;
    }

    public int getNodesExpaned()
    {
        int nodes = 0;
        Node currentNode = this;
        while(currentNode.parent != null)
        nodes++;

        return nodes;
    }
    
        
        
    }

