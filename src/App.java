import code.main.LLAPSearch;

public class App {
        public static void main(String[] args) throws Exception {
                String initialState0 = "17;" +
                                "49,30,46;" +
                                "7,57,6;" +
                                "7,1;20,2;29,2;" +
                                "350,10,9,8,28;" +
                                "408,8,12,13,34;";
                String initialState1 = "50;" +
                                "12,12,12;" +
                                "50,60,70;" +
                                "30,2;19,2;15,2;" +
                                "300,5,7,3,20;" +
                                "500,8,6,3,40;";
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
                String initialState5 = "72;" +
                                "36,13,35;" +
                                "75,96,62;" +
                                "20,2;5,2;33,2;" +
                                "30013,7,6,3,36;" +
                                "40050,5,10,14,44;";

                String initialState8 = "93;" +
                                "46,42,46;" +
                                "5,32,24;" +
                                "13,2;24,1;20,1;" +
                                "155,7,5,10,7;" +
                                "5,5,5,4,4;";
                String initialState10 = "32;" +
                                "20,16,11;" +
                                "76,14,14;" +
                                "9,1;9,2;9,1;" +
                                "358,14,25,23,39;" +
                                "5024,20,17,17,38;";
                System.out.print(LLAPSearch.solve(initialState10, "BF", false));
        }
}
