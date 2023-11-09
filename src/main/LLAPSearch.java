package main;

import constants.Constants;
import enums.Actions;
import interfaces.SearchStrategy;
import models.Node;
import models.State;
import strategies.IterativeDeepeningSearch;

public class LLAPSearch extends GenericSearch {

    private int initialProsperity;
    private int[] initialResources; // food, materials, energy
    private int[] unitPrices; // food, materials, energy
    private int[] foodRequest; // amount, delay
    private int[] materialRequest; // amount, delay
    private int[] energyRequest; // amount, delay
    private int[] buildOneInfo; // price, food, materials, energy, prosperity
    private int[] buildTwoInfo; // price, food, materials, energy, prosperity

    public String solve(String initialState, SearchStrategy strategy, boolean vizualize) throws Exception {
        if (strategy instanceof IterativeDeepeningSearch) {
            IterativeDeepeningSearch newStrategy = (IterativeDeepeningSearch) strategy;
            return super.solveIterative(initialState, strategy, vizualize, newStrategy.getMaxDepth());
        }
        return super.solve(initialState, strategy, vizualize);
    }

    @Override
    public Node makeNodeFromProblem(String initialState) throws Exception {
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

    int[] ConvertValuesToInt(String[] info) {
        int[] returnArray = new int[5];
        for (int i = 0; i < info.length; i++) {
            returnArray[i] = Integer.parseInt(info[i]);
        }
        return returnArray;
    }

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
                    Actions.BUILDONE);
            Node buildTwoNode = node.nextNode(
                    currentState.useResources(buildTwoInfo[0], buildTwoInfo[1], buildTwoInfo[2], buildTwoInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildTwoInfo[4]),
                    Actions.BUILDTWO);
            expanded[0] = waitNode;
            expanded[1] = buildOneNode;
            expanded[2] = buildTwoNode;
            return expanded;
        } else {

            State newState = currentState.copy();
            if (currentState.deliveryArrived()) {
                switch (currentState.getDeliveryType()) {
                    case RequestFood:
                        newState = newState.addResources(foodRequest[0], 0, 0);
                        break;
                    case RequestMaterials:
                            newState = newState.addResources(0, materialRequest[0], 0);
                            break;
                    case RequestEnergy:
                        newState = newState.addResources(0, 0, energyRequest[0]);
                        break;
                    default:
                        break;
                }
            }

            Node[] expandedNodes = new Node[5];
            Node requestFoodNode = node.nextNode(
                newState.requestResource(foodRequest[1],
                    Actions.RequestFood, unitPrices[0], unitPrices[1], unitPrices[2]), Actions.RequestFood);
            Node requestEnergyNode = node.nextNode(
                newState.requestResource(energyRequest[1],
                    Actions.RequestEnergy, unitPrices[0], unitPrices[1], unitPrices[2]), Actions.RequestEnergy);
            Node requestMaterialsNode = node.nextNode(
                newState.requestResource(materialRequest[1],
                    Actions.RequestMaterials, unitPrices[0], unitPrices[1], unitPrices[2]), Actions.RequestMaterials);
            Node buildOneNode = node.nextNode(
                    newState.useResources(buildOneInfo[0], buildOneInfo[1], buildOneInfo[2], buildOneInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildOneInfo[4]),
                    Actions.BUILDONE);
            Node buildTwoNode = node.nextNode(
                    newState.useResources(buildTwoInfo[0], buildTwoInfo[1], buildTwoInfo[2], buildTwoInfo[3],
                            unitPrices[0], unitPrices[1], unitPrices[2], buildTwoInfo[4]),
                    Actions.BUILDTWO);
            expandedNodes[0] = requestFoodNode;
            expandedNodes[1] = requestEnergyNode;
            expandedNodes[2] = requestMaterialsNode;
            expandedNodes[3] = buildOneNode;
            expandedNodes[4] = buildTwoNode;
            return expandedNodes;
        }
    }

    @Override
    public boolean isBlocked(Node node) {
        return node.state.stateBlocked();
    }

    @Override
    public boolean goalTest(Node node) {
        return node.state.getProsperityLevel() >= Constants.prosperityGoal;
    }

    public static void main(String[] args) throws Exception {
        String intialState = "50;" + // initialProsperity
                "22,22,22;" + // initalResources
                "50,60,70;" + // unitPrices
                "30,2;19,1;15,1;" + // foodRequest;materialRequest;energyRequest
                "300,5,7,3,20;" + // buildOneInfo
                "500,8,6,3,40;"; // buildTwoInfo

        LLAPSearch demo = new LLAPSearch();
        demo.makeNodeFromProblem(intialState);
        // for(int i= 0; i< demo.initialResources.length; i++){
        // System.out.println(demo.buildTwoInfo[i]);
        // }
        System.out.println(demo.initialProsperity);

    }

    public int[] getBuildOneInfo() { 
    return buildOneInfo;
    }

    public int[] getBuildTwoInfo() { 
    return buildOneInfo;
    }

    public int[] getUnitPrices() { 
    return unitPrices;
    }
 

}