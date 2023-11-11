package code.models;

import java.util.Comparator;

public class NodeStarByBuild implements Comparator<Node> {

    public NodeStarByBuild() {
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
                return 0;
            }
        }
    }

}
