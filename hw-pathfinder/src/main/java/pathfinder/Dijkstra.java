package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * Not an ADT. Implements Dijkstra's algorithm to find the shortest Path
 * between two separate nodes
 */
public class Dijkstra {

    /**
     * comment soon
     * @param graph todo
     * @param start todo
     * @param dest todo
     * @return todo
     * @param <T> todo
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
            PriorityQueue<Path<T>> active = new PriorityQueue<>(new PathComparator<T>());
            Set<T> finished = new HashSet<>();
            active.add(new Path<T>(start));
            while (!(active.isEmpty())) {
                Path<T> minPath = active.remove();
                T minDest = minPath.getEnd();
                if (minDest.equals(dest)) {
                    return minPath;
                } else {
                    List<Graph<T, Double>.Edge> children = new ArrayList<>(graph.listEdges(minDest));
                    for (Graph<T, Double>.Edge edge : children) {
                        Path<T> newPath = minPath.extend(edge.getChild(), edge.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
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
