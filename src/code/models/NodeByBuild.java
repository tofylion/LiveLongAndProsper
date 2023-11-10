package code.models;

import code.main.LLAPSearch;
import code.constants.Constants;
import java.util.Comparator;

public class NodeByBuild implements Comparator<Node> {

    public Constants Constants;
    private int[] buildOneInfo = LLAPSearch.getBuildOneInfo();
    private int[] buildTwoInfo = LLAPSearch.getBuildTwoInfo();
    int priceBuildOne;
    int priceBuildTwo;
    int prosperityBuildOne;
    int prosperityBuildTwo;
    int costNodeOneToGoal;
    int costNodeTwoToGoal;

    // checks if this instance is being used as a tiebreaker
    boolean isTieBreaking;

    public NodeByBuild(boolean isTieBreaking) {
        // System.out.println(buildOneInfo.toString());
        priceBuildOne = buildOneInfo[0];
        priceBuildTwo = buildTwoInfo[0];
        prosperityBuildOne = buildOneInfo[4];
        prosperityBuildTwo = buildTwoInfo[4];
        this.isTieBreaking = isTieBreaking;
    }

    public int getBetterBuild() {
        if ((prosperityBuildOne / priceBuildOne) > (prosperityBuildTwo / priceBuildTwo)) {
            return 1;
        } else {
            return 2;
        }
    }

    public double[] getCost(Node n1, Node n2) {
        double[] CostOneAndTwo = { 0, 0 };
        int betterBuild = getBetterBuild();
        if (betterBuild == 1) {
            costNodeOneToGoal = ((code.constants.Constants.prosperityGoal - n1.state.getProsperityLevel())
                    / prosperityBuildOne) * priceBuildOne;
            costNodeTwoToGoal = ((code.constants.Constants.prosperityGoal - n2.state.getProsperityLevel())
                    / prosperityBuildOne) * priceBuildOne;

        } else {
            costNodeOneToGoal = ((code.constants.Constants.prosperityGoal - n1.state.getProsperityLevel())
                    / prosperityBuildTwo) * priceBuildTwo;
            costNodeTwoToGoal = ((code.constants.Constants.prosperityGoal - n2.state.getProsperityLevel())
                    / prosperityBuildTwo) * priceBuildTwo;

        }
        CostOneAndTwo[0] = costNodeOneToGoal;
        CostOneAndTwo[1] = costNodeOneToGoal;
        return CostOneAndTwo;
    }

    @Override
    public int compare(Node n1, Node n2) {
        double[] Cost = getCost(n1, n2);
        if (Cost[0] < Cost[1]) {
            return 1;
        } else if (Cost[0] > Cost[1]) {
            return -1;
        } else {
            if (isTieBreaking) {
                return 0;
            } else {
                Comparator<Node> TieBreakingHeuristic = new NodeByResources(true);
                return TieBreakingHeuristic.compare(n1, n2);
            }
        }
    }

}