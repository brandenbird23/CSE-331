package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Not an ADT.
 * Implements Dijkstra's algorithm to find the shortest Path
 * between two separate nodes
 */
public class Dijkstra {

    /**
     * Finds the shortest path between two nodes (start and dest) using Dijkstra's
     * algorithm.
     * @param graph graph to search for path
     * @param start node the search will start at
     * @param dest node the search will end at
     * @param <T> the type of node
     * @return the path object representing the shortest path from start to dest, including the cost
     */
    public static <T> Path<T> findPath(Graph<T, Double> graph, T start, T dest) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        } else if (start == null) {
            throw new IllegalArgumentException("Starting node cannot be null");
        } else if (dest == null) {
            throw new IllegalArgumentException("Destination node cannot be null");
        } else if (!(graph.containsNode(start))) {
            throw new IllegalArgumentException("Graph doesn't contain starting node");
        } else if (!(graph.containsNode(dest))) {
            throw new IllegalArgumentException("Graph doesn't contain destination node");
        } else {
            // Create priority queue to store the active paths. Lowest cost path is in the front
            PriorityQueue<Path<T>> active = new PriorityQueue<>(new PathComparator<>());
            // Store the nodes with the shortest path found
            Set<T> finished = new HashSet<>();
            active.add(new Path<>(start));
            // Loop until the priority queue is empty ot destination node has been found
            while (!(active.isEmpty())) {
                Path<T> minPath = active.remove();
                T minDest = minPath.getEnd();
                // If last node in path is destination node, return the path
                if (minDest.equals(dest)) {
                    return minPath;
                // Skip if last node is processed already
                } else if (finished.contains(minDest)) {
                    continue;
                }
                // Loop over edges of the last node in the path
                for (Graph<T, Double>.Edge edge : graph.listEdges(minDest)) {
                    T child = edge.getChild();
                    double cost = edge.getLabel();
                    // If child node has been processed, skip it
                    if (!(finished.contains(child))) {
                        Path<T> newPath = minPath.extend(child, cost);
                        active.add(newPath);
                    }
                }
                // Mark last node as processed
                finished.add(minDest);
            }
        }
        // If not found, return null
        return null;
    }

    private static class PathComparator<T> implements Comparator<Path<T>> {
        @Override
        public int compare(Path<T> path1, Path<T> path2) {
            double path1Cost = path1.getCost();
            double path2Cost = path2.getCost();
            if (path1Cost == path2Cost) {
                return 0;
            } else if (path1Cost > path2Cost) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
