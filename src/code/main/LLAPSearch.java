package code.main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import code.treeprinter.SimpleTreeNode;
import code.enums.Actions;
import code.interfaces.SearchStrategy;
import code.models.Node;
import code.models.NodeByBuild;
import code.models.NodeByResources;
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
    private static int[] foodRequest; // amount, delay
    private static int[] materialRequest; // amount, delay
    private static int[] energyRequest; // amount, delay
    private static int[] buildOneInfo; // price, food, materials, energy, prosperity
    private static int[] buildTwoInfo; // price, food, materials, energy, prosperity
    private static Set<String> expandedStates;

    public static String solve(String initialState, String strategyString, boolean vizualize) throws Exception {
        expandedStates = new HashSet<String>();
        Node root = makeNodeFromProblem(initialState);
        expandedStates.add(root.state.toString());
        SearchStrategy strategy = decodeStrategy(strategyString);
        if (strategy instanceof IterativeDeepeningSearch) {
            IterativeDeepeningSearch newStrategy = (IterativeDeepeningSearch) strategy;
            return solveIterative(initialState, strategy, vizualize, newStrategy.getMaxDepth());
        }
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;

        Queue<Node> nodes = new LinkedList<Node>();
        nodes.add(root);

        while (true) {
            if (nodes.isEmpty())
                return "nosolution";
            Node node = nodes.remove();
            if (goalTest(node) && !isBlocked(node)) {
                // Record after values
                long[] newStats = getStats();

                // Calculate differences
                long runningTime = newStats[0] - stats[0];
                long cpuUsage = newStats[1] - stats[1];
                long ramUsage = newStats[2] - stats[2];

                // Print results
                System.out.println("Running time: " + runningTime + " ns");
                System.out.println("CPU usage: " + cpuUsage + " ns");
                System.out.println("RAM usage: " + ramUsage + " bytes");
                System.out.println("Nodes expanded: " + nodesExpanded);

                return node.getPath();
            }
            Node[] expanded = !isBlocked(node) ? expand(node) : new Node[0];
            if (vizualize) {
                SimpleTreeNode[] childrenViz = new SimpleTreeNode[expanded.length];
                for (int i = 0; i < childrenViz.length; i++) {
                    childrenViz[i] = expanded[i].toSimpleTreeNode();
                }
                node.addSimpleTreeChildren(childrenViz);
                vizualize(root.toSimpleTreeNode());
            }
            nodes = strategy.queueingFunction(nodes, expanded);
            nodesExpanded++;
        }
    }

    public static String solveIterative(String initialState, SearchStrategy strategy, boolean vizualize, int maxDepth)
            throws Exception {
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;
        // Perform depth-limited search with increasing depth limits
        for (int depth = 0; depth <= maxDepth; depth++) {

            Queue<Node> nodes = new LinkedList<Node>();
            Node root = makeNodeFromProblem(initialState);
            nodes.add(root);
            expandedStates.add(root.state.toString());

            while (true) {
                if (nodes.isEmpty())
                    break;
                Node node = nodes.remove();
                if (goalTest(node) && !isBlocked(node)) {
                    // Record after values
                    long[] newStats = getStats();

                    // Calculate differences
                    long runningTime = newStats[0] - stats[0];
                    long cpuUsage = newStats[1] - stats[1];
                    long ramUsage = newStats[2] - stats[2];

                    // Print results
                    System.out.println("Running time: " + runningTime + " ns");
                    System.out.println("CPU usage: " + cpuUsage + " ns");
                    System.out.println("RAM usage: " + ramUsage + " bytes");
                    System.out.println("Nodes expanded: " + nodesExpanded);

                    return node.getPath();
                }
                Node[] expanded = node.depth < depth && !isBlocked(node) ? expand(node) : new Node[0];
                if (vizualize) {
                    SimpleTreeNode[] childrenViz = new SimpleTreeNode[expanded.length];
                    for (int i = 0; i < childrenViz.length; i++) {
                        childrenViz[i] = expanded[i].toSimpleTreeNode();
                    }
                    node.addSimpleTreeChildren(childrenViz);
                    vizualize(root.toSimpleTreeNode());
                }

                nodes = strategy.queueingFunction(nodes, expanded);
                nodesExpanded++;
            }
        }

        return "nosolution";
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

    public static Node[] expand(Node node) {
        /// Rules:
        /// Cannot wait unless delivery is pending
        /// Cannot exceed more than 50 of each resource
        /// Cannot request more if a delivery is pending

        State currentState = node.state;
        if (currentState.isDeliveryPending()) {
            Node[] expanded = new Node[3];
            int indexReached = 0;
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
            expanded[0] = waitNode;
            expanded[1] = buildOneNode;
            expanded[2] = buildTwoNode;            
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
            int indexReached = 0;
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
            expandedNodes[0] = requestFoodNode;
            expandedNodes[1] = requestEnergyNode;
            expandedNodes[2] = requestMaterialsNode;
            expandedNodes[3] = buildOneNode;
            expandedNodes[4] = buildTwoNode;
            return expandedNodes;
        }
    }

    public static boolean isBlocked(Node node) {
        return node.state.stateBlocked();
    }

    public static boolean goalTest(Node node) {
        return node.state.getProsperityLevel() >= code.constants.Constants.prosperityGoal;
    }

    public static SearchStrategy decodeStrategy(String strategy){
        switch(strategy){
            case "BF": return new BFS();
            case "DF": return new DepthFirstSearch();
            case "UC": return new UniformCost();
            case "ID": return new IterativeDeepeningSearch(100000);
            case "GR1": return new GreedySearch(new NodeByBuild(false), new NodeByResources(true));
            case "GR2": return new GreedySearch(new NodeByResources(false), new NodeByBuild(true));
            case "AS1": return new AStar(new NodeByBuild(false), new NodeByResources(true));
            case "AS2": return new GreedySearch(new NodeByResources(false), new NodeByBuild(true));
            default: return new BFS(); 
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
 

}