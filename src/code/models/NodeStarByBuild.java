package code.models;

import java.util.Comparator;

public class NodeStarByBuild implements Comparator<Node> {

    Comparator<Node> tieBreakingHeuristic;
    boolean isTieBreaking;

    public NodeStarByBuild() {
        isTieBreaking = true;
    }

    public NodeStarByBuild(Comparator<Node> tieBreakingHeuristic) {
        this();
        this.tieBreakingHeuristic = tieBreakingHeuristic;
        this.isTieBreaking = false;
    }

    @Override
    public int compare(Node o1, Node o2) {
        NodeByBuild hOfn = new NodeByBuild();
        double costNode1 = hOfn.getCost(o1) + o1.state.getMoneySpent();
        double costNode2 = hOfn.getCost(o2) + o2.state.getMoneySpent();

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
                    // NodeByResources hOfnTwo = (NodeByResources) (hOfn.tieBreakingHeuristic);
                    // double[] hn2 = { hOfnTwo.getCost(o1), hOfnTwo.getCost(o2) };
                    // costNode1 = hn2[0] + o1.state.getMoneySpent();
                    // costNode2 = hn2[1] + o2.state.getMoneySpent();
                }
            }
        }
    }

}
