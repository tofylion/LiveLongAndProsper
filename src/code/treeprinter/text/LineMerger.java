package code.treeprinter.text;

@FunctionalInterface
public interface LineMerger {

    public String merge(String existingLine, int fromPosition, String replacement);
    
}
