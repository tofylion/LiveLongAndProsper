package code.models;

import code.main.LLAPSearch;
import java.util.Comparator;

public class NodeByResources implements Comparator<Node> {

    int[] resourcesPerBuild1;
    int[] resourcesPerBuild2;
    int[] pricesPerBuild;

    // check if this instance is being used as a tiebreaker only
    boolean isTieBreaking;

    public NodeByResources(boolean isTieBreaking) {
        this.isTieBreaking = isTieBreaking;
        resourcesPerBuild1 = getSlice(LLAPSearch.getBuildOneInfo(), 1, 5);
        resourcesPerBuild2 = getSlice(LLAPSearch.getBuildTwoInfo(), 1, 5);
        pricesPerBuild = LLAPSearch.getUnitPrices();
    }

    public int getOptimalBuild() {
        int ProsperityPerFoodOne = resourcesPerBuild1[3] / resourcesPerBuild1[0];
        int ProsperityPerFoodTwo = resourcesPerBuild2[3] / resourcesPerBuild2[0];

        int maxRatio = Math.max(ProsperityPerFoodTwo, ProsperityPerFoodOne);

        return maxRatio == ProsperityPerFoodOne ? 1 : 2;
    }

    public double getTotalFood(int build) {
        int numberOfBuilds = code.constants.Constants.prosperityGoal / build == 1 ? resourcesPerBuild1[0]
                : resourcesPerBuild2[0];
        double totalFood = numberOfBuilds * build == 1 ? resourcesPerBuild1[1] : resourcesPerBuild2[2];
        return totalFood;
    }

    public double getCost(Node n1) {
        int optimalBuild = getOptimalBuild();
        double totalFoodNeeded = getTotalFood(optimalBuild);
        double totalCost = totalFoodNeeded * pricesPerBuild[0];

        return totalCost;
    }

    @Override
    public int compare(Node n1, Node n2) {
        if (getCost(n1) > getCost(n2)) {
            return -1;
        } else if (getCost(n1) < getCost(n2)) {
            return 1;
        } else {
            if (isTieBreaking) {
                return 0;
            } else {
                Comparator<Node> TieBreakingHeuristic = new NodeByBuild(true);
                return TieBreakingHeuristic.compare(n1, n2);
            }
        }
    }

    /**
     * Returns a slice of the given array from the specified start index (inclusive)
     * to the specified end index (exclusive).
     *
     * @param array      the array to slice
     * @param startIndex the starting index of the slice (inclusive)
     * @param endIndex   the ending index of the slice (exclusive)
     * @return the sliced array
     */
    public int[] getSlice(int[] array, int startIndex, int endIndex) {
        int[] slicedArray = new int[endIndex - startIndex];
        for (int i = 0; i < slicedArray.length; i++) {
            slicedArray[i] = array[startIndex + i];
        }
        return slicedArray;
    }

}