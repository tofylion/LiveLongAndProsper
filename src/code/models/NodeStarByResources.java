package code.models;

import java.util.Comparator;

public class NodeStarByResources implements Comparator<Node> {

    Comparator<Node> tieBreakingHeuristic;
    boolean isTieBreaking;

    public NodeStarByResources() {
        isTieBreaking = true;
    }

    public NodeStarByResources(Comparator<Node> tieBreakingHeuristic) {
        this();
        this.tieBreakingHeuristic = tieBreakingHeuristic;
        this.isTieBreaking = false;
    }

    @Override
    public int compare(Node o1, Node o2) {
        NodeByResources hOfn = new NodeByResources();
        double[] hn2 = { hOfn.getCost(o1), hOfn.getCost(o2) };
        double costNode1 = hn2[0] + o1.state.getMoneySpent();
        double costNode2 = hn2[1] + o2.state.getMoneySpent();

        while (true) {
            if (costNode1 < costNode2) {
                return 1;
            } else if (costNode1 > costNode2) {
                return -1;
            } else {
                if (isTieBreaking || tieBreakingHeuristic == null) {
                    return 0;
                } else {
                    return tieBreakingHeuristic.compare(o1, o2);
                }
            }
        }
    }

}
