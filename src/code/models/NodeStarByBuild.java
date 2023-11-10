package code.models;

import java.util.Comparator;

public class NodeStarByBuild implements Comparator<Node> {

    boolean isTieBreaking;

    public NodeStarByBuild(boolean isTieBreaking) {
        this.isTieBreaking = isTieBreaking;
    }

    @Override
    public int compare(Node o1, Node o2) {
        NodeByBuild hOfn = new NodeByBuild(isTieBreaking);
        double[] hn1 = hOfn.getCost(o1, o2);
        double costNode1 = hn1[0] + o1.state.getMoneySpent();
        double costNode2 = hn1[1] + o2.state.getMoneySpent();

        while (true) {
            if (costNode1 < costNode2) {
                return 1;
            } else if (costNode1 > costNode2) {
                return -1;
            } else {
                if (isTieBreaking) {
                    return 0;
                } else {
                    NodeByResources hOfnTwo = new NodeByResources(isTieBreaking);
                    double[] hn2 = { hOfnTwo.getCost(o1), hOfnTwo.getCost(o2) };
                    costNode1 = hn2[0] + o1.state.getMoneySpent();
                    costNode2 = hn2[1] + o2.state.getMoneySpent();
                }
            }
        }
    }

}
