package code.treeprinter.printer.traditional;

import java.util.List;

import code.treeprinter.text.LineBuffer;

/**
 * Liner strategy interface for {@link TraditionalTreePrinter}
 */
public interface Liner {
    
    public int printConnections(LineBuffer buffer, int row, int topConnection, List<Integer> bottomConnections);
    
}