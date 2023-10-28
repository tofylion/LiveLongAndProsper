package models;

public class Node implements Comparable<Node>{

    //the cost to reach this node from its parent
    public final int cost;

    public Node(int cost) {
        //TODO: constructor body
        this.cost = cost;
    }

    public String getPath() {
        //TODO: implement getPath
        // Return is of the following format: plan;monetaryCost;nodesExpanded
        throw new UnsupportedOperationException();
    }

    public int compareTo(Node anotherNode){
        //if this node's cost is less than the other one then return -1.
        //if it is more than the other one return 1.
        //if they are equal return 0 (can be replaced with a tiebreaker in the future).
        if (this.cost < anotherNode.cost){
            return -1;
        } else if (this.cost > anotherNode.cost){
            return 1;
        } else{
            return 0;
        }
    }
}
