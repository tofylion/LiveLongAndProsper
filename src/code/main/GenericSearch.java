package code.main;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import code.treeprinter.SimpleTreeNode;
import code.treeprinter.printer.listing.ListingTreePrinter;
import code.interfaces.SearchInterface;
import code.interfaces.SearchStrategy;
import code.models.Node;

abstract public class GenericSearch implements SearchInterface {
    public GenericSearch() {

    }

    public static String solve(Node root, SearchStrategy strategy, boolean vizualize, GenericSearch problem)
            throws Exception {
        HashSet<String> allExpandedStates = new HashSet<String>();
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;

        Queue<Node> nodes = new LinkedList<Node>();
        nodes.add(root);
        allExpandedStates.add(root.state.toFullString());

        while (true) {
            if (nodes.isEmpty()) {
                if (vizualize)
                    vizualize(root.toSimpleTreeNode());
                return "NOSOLUTION";
            }
            Node node = nodes.remove();
            if (problem.goalTest(node) && !problem.isBlocked(node)) {

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

                if (vizualize) {
                    vizualize(root.toSimpleTreeNode());
                    vizualize(node.toSimpleTreeNode());
                }

                return node.getPath() + ";" + nodesExpanded;
            }

            Node[] expanded = !(problem.isBlocked(node)) ? problem.expand(node) : new Node[0];

            int nonRepeatedCount = 0;

            for (int i = 0; i < expanded.length; i++) {
                if (!allExpandedStates.contains(expanded[i].state.toFullString()) && !problem.isBlocked(expanded[i])) {
                    nonRepeatedCount++;
                }
            }

            Node[] nonRepeatedExpanded = new Node[nonRepeatedCount];

            int j = 0;
            for (int i = 0; i < expanded.length; i++) {
                if (!allExpandedStates.contains(expanded[i].state.toFullString()) && !problem.isBlocked(expanded[i])) {
                    nonRepeatedExpanded[j] = expanded[i];
                    allExpandedStates.add(expanded[i].state.toFullString());
                    j++;
                }
            }

            if (vizualize) {
                SimpleTreeNode[] childrenViz = new SimpleTreeNode[nonRepeatedExpanded.length];
                for (int i = 0; i < childrenViz.length; i++) {
                    childrenViz[i] = nonRepeatedExpanded[i].toSimpleTreeNode();
                }
                node.addSimpleTreeChildren(childrenViz);
            }

            nodes = strategy.queueingFunction(nodes, nonRepeatedExpanded);
            nodesExpanded++;
        }
    }

    public static String solveIterative(Node root, SearchStrategy strategy, boolean vizualize, int maxDepth,
            GenericSearch problem)
            throws Exception {
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;
        // Perform depth-limited search with increasing depth limits
        for (int depth = 0; depth <= maxDepth; depth++) {
            HashSet<String> allExpandedStates = new HashSet<String>();
            Queue<Node> nodes = new LinkedList<Node>();
            nodes.add(root);
            allExpandedStates.add(root.state.toFullString());

            while (true) {
                if (nodes.isEmpty())
                    break;
                Node node = nodes.remove();
                if (problem.goalTest(node) && !problem.isBlocked(node)) {

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
                    if (vizualize) {
                        vizualize(root.toSimpleTreeNode());
                        vizualize(node.toSimpleTreeNode());
                    }

                    return node.getPath() + ";" + nodesExpanded;
                }
                Node[] expanded = node.depth <= depth && !problem.isBlocked(node) ? problem.expand(node) : new Node[0];
                int nonRepeatedCount = 0;

                for (int i = 0; i < expanded.length; i++) {
                    if (!allExpandedStates.contains(expanded[i].state.toFullString()) && !problem.isBlocked(expanded[i])) {
                        nonRepeatedCount++;
                    }
                }

                Node[] nonRepeatedExpanded = new Node[nonRepeatedCount];
                int j = 0;
                for (int i = 0; i < expanded.length; i++) {
                    if (!allExpandedStates.contains(expanded[i].state.toFullString()) && !problem.isBlocked(expanded[i])) {
                        nonRepeatedExpanded[j] = expanded[i];
                        allExpandedStates.add(expanded[i].state.toFullString());
                        j++;
                    }
                }

                if (vizualize) {
                    SimpleTreeNode[] childrenViz = new SimpleTreeNode[nonRepeatedExpanded.length];
                    for (int i = 0; i < childrenViz.length; i++) {
                        childrenViz[i] = nonRepeatedExpanded[i].toSimpleTreeNode();
                    }
                    node.addSimpleTreeChildren(childrenViz);
                }

                nodes = strategy.queueingFunction(nodes, nonRepeatedExpanded);
                nodesExpanded++;
            }
            if (vizualize)
                vizualize(root.toSimpleTreeNode());
        }

        return "NOSOLUTION";
    }

    /**
     * Returns an array of long values representing the start time, CPU time, and
     * memory usage of the current thread.
     *
     * @return an array of long values representing the start time, CPU time, and
     *         memory usage of the current thread.
     */
    private static long[] getStats() {
        long startTime = System.nanoTime();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long startCpu = bean.getCurrentThreadCpuTime();
        long startRam = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return new long[] { startTime, startCpu, startRam };
    }

    public boolean isBlocked(Node node) {
        throw new UnsupportedOperationException();
    }

    public boolean goalTest(Node node) {
        throw new UnsupportedOperationException();
    }

    public Node[] expand(Node node) {
        throw new UnsupportedOperationException();
    }

    public static void vizualize(SimpleTreeNode rootNode) {
        new ListingTreePrinter().print(rootNode);
    }

}
