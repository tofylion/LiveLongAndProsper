package code.treeprinter.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import code.treeprinter.AnsiMode;
import code.treeprinter.TreeNode;
import code.treeprinter.text.AnsiLineMerger;
import code.treeprinter.text.ConsoleText;
import code.treeprinter.text.LineBuffer;
import code.treeprinter.text.LineMerger;
import code.treeprinter.text.PlainLineMerger;

public final class Util {
    
    private Util() {
        // utility class
    }


    public static String getStringContent(ConsoleText content, AnsiMode ansiMode) {
        return ansiMode.isEnabled() ? content.ansi() : content.plain();
    }

    public static ConsoleText toConsoleText(String stringContent, AnsiMode ansiMode) {
        return ansiMode.isEnabled() ? ConsoleText.ofAnsi(stringContent) : ConsoleText.of(stringContent);
    }

    public static LineBuffer createLineBuffer(Appendable out, AnsiMode ansiMode) {
        LineMerger lineMerger = ansiMode.isEnabled() ? new AnsiLineMerger() : new PlainLineMerger();
        return new LineBuffer(out, lineMerger, ansiMode);
    }
    
    public static int getDepth(TreeNode treeNode) {
        List<TreeNode> levelNodes = new ArrayList<>();
        levelNodes.add(treeNode);
        int depth = 0;
        while (true) {
            List<TreeNode> newLevelNodes = new ArrayList<>();
            for (TreeNode levelNode: levelNodes) {
                for (TreeNode childNode: levelNode.children()) {
                    if (childNode != null) {
                        newLevelNodes.add(childNode);
                    }
                }
            }
            if (newLevelNodes.isEmpty()) {
                break;
            }
            levelNodes = newLevelNodes;
            depth++;
        }
        return depth;
    }

    public static void write(Appendable out, String content) {
        try {
            out.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeln(Appendable out, String content) {
        write(out, content + "\n");
    }
    
}
