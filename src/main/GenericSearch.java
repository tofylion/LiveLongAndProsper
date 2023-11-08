package main;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;


import java.util.LinkedList;
import java.util.Queue;

import interfaces.SearchInterface;
import interfaces.SearchStrategy;
import models.Node;

abstract public class GenericSearch implements SearchInterface {
    
    @Override
    public String solve(String initialState, SearchStrategy strategy, boolean vizualize) throws Exception{
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;

        Queue<Node> nodes = new LinkedList<Node>();
        Node root = makeNodeFromProblem(initialState);
        nodes.add(root);

        while (true) {
            if (nodes.isEmpty())
                return "NOSOLUTION";
            Node node = nodes.remove();
            if (goalTest(node)) {
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
            nodes = strategy.queueingFunction(nodes, expanded);
            nodesExpanded++;
        }
    }
    
    public String solveIterative(String initialState, SearchStrategy strategy, boolean vizualize, int maxDepth) throws Exception {
        // Record before values
        long[] stats = getStats();
        int nodesExpanded = 0;

        Queue<Node> nodes = new LinkedList<Node>();
        Node root = makeNodeFromProblem(initialState);
        nodes.add(root);

        // Perform depth-limited search with increasing depth limits
        for (int depth = 0; depth <= maxDepth; depth++) {

            while (true) {
            if (nodes.isEmpty())
                return "NOSOLUTION";
            Node node = nodes.remove();
            if (goalTest(node)) {
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
            nodes = strategy.queueingFunction(nodes, expanded);
            nodesExpanded++;
        }
        }

        return "NOSOLUTION";
    }

    
    /**
     * Returns an array of long values representing the start time, CPU time, and memory usage of the current thread.
     *
     * @return an array of long values representing the start time, CPU time, and memory usage of the current thread.
     */
    private long[] getStats()
    {
        long startTime = System.nanoTime();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long startCpu = bean.getCurrentThreadCpuTime();
        long startRam = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return new long[]{startTime, startCpu, startRam};
    }

    @Override
    public Node makeNodeFromProblem(String problem) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean goalTest(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node[] expand(Node node) {
        throw new UnsupportedOperationException();
    }

    public void vizualize(Node node) {
        //TODO: implement vizualize
        throw new UnsupportedOperationException();
    }

}
