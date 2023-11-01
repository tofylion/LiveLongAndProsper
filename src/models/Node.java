package models;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;

import models.State;

public class Node implements Comparable<Node>{

    //the cost to reach this node from its parent
    private double monetaryCost;

    
public class Node implements Comparable<Node> {
    private double monetaryCost;
    private Node parent;
    public State state;
    public String previousAction;
    public int depth;

    public Node(int monetaryCost) {
        //TODO: constructor body
        this.monetaryCost = monetaryCost;
    }
  
    public Node(double monetaryCost, int nodesExpanded, Node parent, State state, String previousAction, int depth) {
       
        this.monetaryCost = monetaryCost;
        this.parent = parent;
        this.state = state;
        this.previousAction = previousAction;
        this.depth = depth;
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

    public int compareTo(Node anotherNode){
        //if this node's cost is less than the other one then return -1.
        //if it is more than the other one return 1.
        //if they are equal return 0 (can be replaced with a tiebreaker in the future).
        if (this.monetaryCost < anotherNode.monetaryCost){
            return -1;
        } else if (this.monetaryCost > anotherNode.monetaryCost){
            return 1;
        } else{
            return 0;
        }
    }
    
    public double getMoneySpent()
    {
        double moneySpent = 0.0;
        Node currentNode = this;
        moneySpent = currentNode.state.getMoneySpent();
        
        return moneySpent;
    }
   
        
    }

