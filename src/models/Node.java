package models;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;

import models.State;

public class Node {
    private double monetaryCost;
    private Node parent;
    private State state;
    private String previousAction;

    public Node(double monetaryCost, int nodesExpanded, Node parent, State state, String previousAction) {
       
        this.monetaryCost = monetaryCost;
        this.parent = parent;
        this.state = state;
        this.previousAction = previousAction;
    }

    public String getPath() {
        
        List<String> planList = getPlan();
        String plan = "";
        monetaryCost = state.getMoneySpent();
        for (int i = 0; i < planList.size(); i++) { 
            plan += " " + planList.get(i) + ",";
        } 
        return String.join(plan + ";" + monetaryCost);
        
    }

    public List<String> getPlan() {
        List<String> accumulatedPlan = new LinkedList<>();
    
        Node currentNode = this;
    
        while (currentNode != null) {
            String previousAction = currentNode.previousAction;
            
            if (previousAction != null) {
                accumulatedPlan.add(0, previousAction);
            }
    
            currentNode = currentNode.parent;
        }
    
        return accumulatedPlan;
    }

    public double getMoneySpent()
    {
        double moneySpent = 0.0;
        Node currentNode = this;
        moneySpent = currentNode.state.getMoneySpent();
        
        return moneySpent;
    }
   
        
    }

