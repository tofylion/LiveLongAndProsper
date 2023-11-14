package code.main;

import code.enums.Actions;
import code.interfaces.SearchStrategy;
import code.models.Node;
import code.models.NodeByBuild;
import code.models.NodeByResources;
import code.models.NodeStarByBuild;
import code.models.NodeStarByResources;
import code.models.State;
import code.strategies.AStar;
import code.strategies.BFS;
import code.strategies.DepthFirstSearch;
import code.strategies.GreedySearch;
import code.strategies.IterativeDeepeningSearch;
import code.strategies.UniformCost;

public class LLAPSearch extends GenericSearch {

    private static int initialProsperity;
    private static int[] initialResources; // food, materials, energy
    private static int[] unitPrices; // food, materials, energy
    public static int[] foodRequest; // amount, delay
    public static int[] materialRequest; // amount, delay
    public static int[] energyRequest; // amount, delay
    private static int[] buildOneInfo; // price, food, materials, energy, prosperity
    private static int[] buildTwoInfo; // price, food, materials, energy, prosperity

    public LLAPSearch() {
    }

    public static String solve(String initialState, String strategyString, boolean vizualize) throws Exception {
        Node root = makeNodeFromProblem(initialState);
        SearchStrategy strategy = decodeStrategy(strategyString);
        if (strategy instanceof IterativeDeepeningSearch) {
            IterativeDeepeningSearch newStrategy = (IterativeDeepeningSearch) strategy;
            return GenericSearch.solveIterative(root, strategy, vizualize, newStrategy.getMaxDepth(),
                    new LLAPSearch());
        }
        return GenericSearch.solve(root, strategy, vizualize, new LLAPSearch());
    }

    public static Node makeNodeFromProblem(String initialState) throws Exception {
        String[] arguments = initialState.split(";");
        if (arguments.length < 8) {
            throw new Exception("Please enter a valid initial state");
        }
        initialProsperity = Integer.parseInt(arguments[0]);
        initialResources = ConvertValuesToInt(arguments[1].split(","));
        unitPrices = ConvertValuesToInt(arguments[2].split(","));
        foodRequest = ConvertValuesToInt(arguments[3].split(","));
        materialRequest = ConvertValuesToInt(arguments[4].split(","));
        energyRequest = ConvertValuesToInt(arguments[5].split(","));
        buildOneInfo = ConvertValuesToInt(arguments[6].split(","));
        buildTwoInfo = ConvertValuesToInt(arguments[7].split(","));
        State initState = new State(initialProsperity, initialResources[0], initialResources[1], initialResources[2], 0,
                0,
                null);
        return new Node(initState);
    }

    static int[] ConvertValuesToInt(String[] info) {
        int[] returnArray = new int[5];
        for (int i = 0; i < info.length; i++) {
            returnArray[i] = Integer.parseInt(info[i]);
        }
        return returnArray;
    }

    @Override
    public Node[] expand(Node node) {
        /// Rules:
        /// Cannot wait unless delivery is pending
        /// Cannot exceed more than 50 of each resource
        /// Cannot request more if a delivery is pending

        State currentState = node.state;
        if (currentState.isDeliveryPending()) {
            Node[] expanded = new Node[3];
            Node waitNode = node.nextNode(
                    currentState.useResources(unitPrices[0], unitPrices[1], unitPrices[2]),
                    Actions.WAIT);
            Node buildOneNode = node.nextNode(
                    currentState.useResources(buildOneInfo[0], buildOneInfo[1], buildOneInfo[2], buildOneInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildOneInfo[4]),
                    Actions.BUILD1);
            Node buildTwoNode = node.nextNode(
                    currentState.useResources(buildTwoInfo[0], buildTwoInfo[1], buildTwoInfo[2], buildTwoInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildTwoInfo[4]),
                    Actions.BUILD2);
            expanded[0] = buildOneNode;
            expanded[1] = buildTwoNode;
            expanded[2] = waitNode;
            return expanded;
        } else {
            State newState = currentState.copy();
            if (currentState.deliveryArrived()) {
                switch (currentState.getDeliveryType()) {
                    case requestfood:
                        newState = newState.addResources(foodRequest[0], 0, 0);
                        break;
                    case requestmaterials:
                        newState = newState.addResources(0, materialRequest[0], 0);
                        break;
                    case requestenergy:
                        newState = newState.addResources(0, 0, energyRequest[0]);
                        break;
                    default:
                        break;
                }
            }

            Node[] expandedNodes = new Node[5];
            Node requestFoodNode = node.nextNode(
                    newState.requestResource(foodRequest[1],
                            Actions.requestfood, unitPrices[0], unitPrices[1], unitPrices[2]),
                    Actions.requestfood);
            Node requestEnergyNode = node.nextNode(
                    newState.requestResource(energyRequest[1],
                            Actions.requestenergy, unitPrices[0], unitPrices[1], unitPrices[2]),
                    Actions.requestenergy);
            Node requestMaterialsNode = node.nextNode(
                    newState.requestResource(materialRequest[1],
                            Actions.requestmaterials, unitPrices[0], unitPrices[1], unitPrices[2]),
                    Actions.requestmaterials);
            Node buildOneNode = node.nextNode(
                    newState.useResources(buildOneInfo[0], buildOneInfo[1], buildOneInfo[2], buildOneInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildOneInfo[4]),
                    Actions.BUILD1);
            Node buildTwoNode = node.nextNode(
                    newState.useResources(buildTwoInfo[0], buildTwoInfo[1], buildTwoInfo[2], buildTwoInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildTwoInfo[4]),
                    Actions.BUILD2);
            expandedNodes[0] = buildOneNode.state.stateOverflow() ? null : buildOneNode;
            expandedNodes[1] = buildTwoNode.state.stateOverflow() ? null : buildTwoNode;
            expandedNodes[2] = requestFoodNode.state.stateOverflow() ? null : requestFoodNode;
            expandedNodes[3] = requestEnergyNode.state.stateOverflow() ? null : requestEnergyNode;
            expandedNodes[4] = requestMaterialsNode.state.stateOverflow() ? null : requestMaterialsNode;
            return expandedNodes;
        }
    }

    @Override
    public boolean isBlocked(Node node) {
        return node.state.stateBlocked();
    }

    @Override
    public boolean goalTest(Node node) {
        return node.state.getProsperityLevel() >= code.constants.Constants.prosperityGoal;
    }

    public static SearchStrategy decodeStrategy(String strategy) {
        switch (strategy) {
            case "BF":
                return new BFS();
            case "DF":
                return new DepthFirstSearch();
            case "UC":
                return new UniformCost();
            case "ID":
                return new IterativeDeepeningSearch(100000);
            case "GR1":
                return new GreedySearch(new NodeByBuild(new NodeByResources()));
            case "GR2":
                return new GreedySearch(new NodeByResources(new NodeByBuild()));
            case "AS1":
                return new AStar(new NodeStarByBuild(new NodeStarByResources()));
            case "AS2":
                return new AStar(new NodeStarByResources(new NodeStarByBuild()));
            default:
                return new BFS();
        }
    }

    public static int[] getBuildOneInfo() {
        return buildOneInfo;
    }

    public static int[] getBuildTwoInfo() {
        return buildTwoInfo;
    }

    public static int[] getUnitPrices() {
        return unitPrices;
    }

    public static void main(String[] args) throws Exception {
        String initialState0 = "17;" +
                "49,30,46;" +
                "7,57,6;" +
                "7,1;20,2;29,2;" +
                "350,10,9,8,28;" +
                "408,8,12,13,34;";

        String initialState3 = "0;" +
                "19,35,40;" +
                "27,84,200;" +
                "15,2;37,1;19,2;" +
                "569,11,20,3,50;" +
                "115,5,8,21,38;";

        String initialState4 = "21;" +
                "15,19,13;" +
                "50,50,50;" +
                "12,2;16,2;9,2;" +
                "3076,15,26,28,40;" +
                "5015,25,15,15,38;";

        String initialState9 = "50;" +
                "20,16,11;" +
                "76,14,14;" +
                "7,1;7,1;7,1;" +
                "359,14,25,23,39;" +
                "524,18,17,17,38;";

        String initialState10 = "32;" +
                "20,16,11;" +
                "76,14,14;" +
                "9,1;9,2;9,1;" +
                "358,14,25,23,39;" +
                "5024,20,17,17,38;";

        String initialState = initialState4;
        LLAPSearch.solve(initialState, "GR1", false);

    }

}