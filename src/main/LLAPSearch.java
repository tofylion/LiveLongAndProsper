package main;
import interfaces.SearchStrategy;
import strategies.IterativeDeepeningSearch;

public class LLAPSearch extends GenericSearch {

    private int initialProsperity;
    private int[] initialResources;
    private int[] unitPrices;
    private int[] foodRequest;
    private int[] materialRequest;
    private int[] energyRequest;
    private int[] buildOneInfo;
    private int[] buildTwoInfo;

    public String solve(String initialState, SearchStrategy strategy, boolean vizualize) throws Exception {
        ParseInitialState(initialState);
        if(strategy instanceof IterativeDeepeningSearch){
            IterativeDeepeningSearch newStrategy = (IterativeDeepeningSearch) strategy;
            return super.solveIterative(initialState,  strategy, vizualize, newStrategy.getMaxDepth());
        }
        return super.solve(initialState, strategy, vizualize);
    }

    void ParseInitialState(String initialState) throws Exception {
        String[] arguments = initialState.split(";");
        if(arguments.length < 8){
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
    }

    int[] ConvertValuesToInt(String[] info){
        int[] returnArray = new int[5];
        for (int i = 0; i < info.length; i++) {
            returnArray[i] = Integer.parseInt(info[i]);
        }
        return returnArray;
    }

    public static void main(String[] args) throws Exception{
        String intialState = 
        "50;"+                  //initialProsperity
        "22,22,22;" +           //initalResources
        "50,60,70;" +           //unitPrices
        "30,2;19,1;15,1;" +     //foodRequest;materialRequest;energyRequest
        "300,5,7,3,20;" +       //buildOneInfo
        "500,8,6,3,40;";        //buildTwoInfo
        
        LLAPSearch demo = new LLAPSearch();
        demo.ParseInitialState(intialState);
        // for(int i= 0; i< demo.initialResources.length; i++){
        //     System.out.println(demo.buildTwoInfo[i]);
        // }
        System.out.println(demo.initialProsperity);

        
    }
}