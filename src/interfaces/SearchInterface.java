package interfaces;

import models.Node;

public interface SearchInterface {
    String solve(String initialState, SearchStrategy strategy, boolean vizualize) throws Exception;
    Node makeNodeFromProblem(String problem);
    boolean goalTest(Node node);
    Node[] expand(Node node);
}
