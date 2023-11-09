package treeprinter.printer.traditional;

/**
 * Value class for storing node placement settings for {@link TraditionalTreePrinter}
 */
public class Placement {

    private final int left;

    private final int topConnection;

    private final int bottomConnection;

    
    public Placement(int left, int topConnection, int bottomConnection) {
        this.left = left;
        this.topConnection = topConnection;
        this.bottomConnection = bottomConnection;
    }

    
    public int left() {
        return left;
    }

    public int topConnection() {
        return topConnection;
    }

    public int bottomConnection() {
        return bottomConnection;
    }
    
}