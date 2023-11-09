package models;
import main.LLAPSearch;
import constants.Constants;
import java.util.Comparator;
import models.Node;


public class NodeByBuild implements Comparator<Node> {

    public LLAPSearch problem;
    public Constants Constants;
    private int[] buildOneInfo;
    private int[] buildTwoInfo;
    int priceBuildOne;
    int priceBuildTwo;
    int prosperityBuildOne;
    int prosperityBuildTwo;
    int costNodeOneToGoal;
    int costNodeTwoToGoal;

    //checks if this instance is being used as a tiebreaker
    boolean isTieBreaking;

    public NodeByBuild(boolean isTieBreaking, LLAPSearch problem){
        this.problem = problem;
        buildOneInfo = problem.getBuildOneInfo();
        buildTwoInfo = problem.getBuildTwoInfo();
        priceBuildOne = buildOneInfo[0];
        priceBuildTwo = buildTwoInfo[0];
        prosperityBuildOne = buildOneInfo[4];
        prosperityBuildTwo = buildTwoInfo[4];
        this.isTieBreaking = isTieBreaking;
    }

    public int getBetterBuild()
    {
       if((prosperityBuildOne / priceBuildOne) > (prosperityBuildTwo / priceBuildTwo)) 
       {
        return 1;
       }
       else
       {
        return 2;
       }
    }

    public int[] getCost(Node n1, Node n2)
    {
        int[] CostOneAndTwo = {0,0};
        int betterBuild = getBetterBuild();
        if(betterBuild == 1)
        {
            costNodeOneToGoal = ((constants.Constants.prosperityGoal - n1.state.getProsperityLevel()) / prosperityBuildOne ) * priceBuildOne ;
            costNodeTwoToGoal = ((constants.Constants.prosperityGoal - n2.state.getProsperityLevel()) / prosperityBuildOne )* priceBuildOne ;

        }
        else 
        {
            costNodeOneToGoal = ((constants.Constants.prosperityGoal - n1.state.getProsperityLevel()) / prosperityBuildTwo )* priceBuildTwo  ;
            costNodeTwoToGoal = ((constants.Constants.prosperityGoal - n2.state.getProsperityLevel()) / prosperityBuildTwo )* priceBuildTwo ;

        }
        CostOneAndTwo[0] = costNodeOneToGoal;
        CostOneAndTwo[1] = costNodeOneToGoal;
        return CostOneAndTwo;
    }

    @Override
    public int compare(Node n1, Node n2) {
        int[] Cost = getCost(n1, n2);
        if (Cost[1] < Cost[0]){
            return -1;
        } else if (Cost[0] > Cost[1]){
            return 1;
        } else{
            if (isTieBreaking) {
                return 0;
            } else {
                Comparator<Node> TieBreakingHeuristic = new NodeByResources(true, pro);
                return TieBreakingHeuristic.compare(n1, n2);
            }
        }
    }





   

    
}