package models;

import java.util.Comparator;

public class NodeByProsperity implements Comparator<Node>{

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.state.getProsperityLevel() < n2.state.getProsperityLevel()){
            return -1;
        } else if (n1.state.getProsperityLevel() > n2.state.getProsperityLevel()){
            return 1;
        } else{
            return 0;
        }
    }
    
}
