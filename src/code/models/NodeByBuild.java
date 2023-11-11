package code.models;

import code.main.LLAPSearch;
import java.util.Comparator;
import java.util.HashMap;

public class NodeByBuild implements Comparator<Node> {

    private int[] buildOneInfo = LLAPSearch.getBuildOneInfo();
    private int[] buildTwoInfo = LLAPSearch.getBuildTwoInfo();
    int priceBuildOne;
    int priceBuildTwo;
    int prosperityBuildOne;
    int prosperityBuildTwo;
    int costNodeOneToGoal;
    int costNodeTwoToGoal;
    HashMap<String, Double> costMap;
    private int optimalBuild;

    public NodeByBuild() {
        priceBuildOne = buildOneInfo[0];
        priceBuildTwo = buildTwoInfo[0];
        prosperityBuildOne = buildOneInfo[4];
        prosperityBuildTwo = buildTwoInfo[4];
        this.costMap = new HashMap<>();
        this.optimalBuild = getBetterBuild();
    }


    public int getBetterBuild() {
        if ((prosperityBuildOne / priceBuildOne) > (prosperityBuildTwo / priceBuildTwo)) {
            return 1;
        } else {
            return 2;
        }
    }

    public double getCost(Node n1) {
        if (costMap.containsKey(Integer.toString(n1.state.getProsperityLevel()))) {
            return costMap.get(Integer.toString(n1.state.getProsperityLevel()));
        } else {
            int betterBuild = optimalBuild;
            double cost = 0;
            if (betterBuild == 1) {
                cost = ((code.constants.Constants.prosperityGoal - n1.state.getProsperityLevel())
                        / prosperityBuildOne)
                        * priceBuildOne;
            } else {
                cost = ((code.constants.Constants.prosperityGoal - n1.state.getProsperityLevel())
                        / prosperityBuildTwo)
                        * priceBuildTwo;
            }
            costMap.put(Integer.toString(n1.state.getProsperityLevel()), cost);
            return cost;
        }
    }

    @Override
    public int compare(Node n1, Node n2) {
        double cost1 = getCost(n1);
        double cost2 = getCost(n2);
        if (cost1 < cost2) {
            return 1;
        } else if (cost1 > cost2) {
            return -1;
        } else {
            return 0;
        }
    }

}