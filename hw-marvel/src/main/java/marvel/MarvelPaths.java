package marvel;
import graph.*;

import java.util.*;


public class MarvelPaths {
    /* Not an ADT */


    public static void main(String[] args) {
        Graph<String, String> marvelGraph = MarvelPaths.graphCreator("marvel.csv");
        Scanner input = new Scanner(System.in);

        //TODO
    }

    /**
     * Builds a graph using the file given as argument. The file given must not be null.
     * @param fileName the file given that the graph builds from
     * @return
     * @throws IllegalArgumentException fileName != null
     */
    public static Graph<String, String> graphCreator(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Given file must not be null");
        } else {
            Graph<String, String> marvelGraph = new Graph<>();
            Map<String, List<String>> comics = MarvelParser.parseData(fileName);
            for (Map.Entry<String, List<String>> entry : comics.entrySet()) {
                String comic = entry.getKey();
                List<String> characters = entry.getValue();

                for (int i = 0; i < characters.size(); i++) {
                    String char1 = characters.get(i);
                    marvelGraph.addNode(char1);

                    for (int j = i + 1; j < characters.size(); j++) {
                        String char2 = characters.get(j);
                        marvelGraph.addNode(char2);
                        marvelGraph.addEdge(char1, char2, comic);
                    }
                }
            }
            return marvelGraph;
        }
    }

    /**
     * Finds the shortest path between characters using breadth-first search.
     * Will return the shortest path between the char1 and char2.
     * @param graph the graph being searched to find the shortest path
     * @param char1 the starting character (node)
     * @param char2 the ending character (node)
     * @return the shortest path between characters (char1 <-> char2)
     * @throws IllegalArgumentException if start or end is not in the graph or graph is null
     */
    public static List<Graph<String, String>.Edge> BFS(Graph<String, String> graph, String char1, String char2) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        } else if (!(graph.containsNode(char1))) {
            throw new IllegalArgumentException(char1);
        } else if (!(graph.containsNode(char2))) {
            throw new IllegalArgumentException(char2);
        } else {
            Queue<String> nodeQueue = new LinkedList<>();
            Map<String, List<Graph<String, String>.Edge>> nodePath = new HashMap<>();
            nodeQueue.add(char1);
            nodePath.put(char1, new ArrayList<>());

            for (String next = nodeQueue.poll(); next != null; next = nodeQueue.poll()) {
                List<Graph<String, String>.Edge> curr = nodePath.get(next);
                if (next.equals(char2)) {
                    return curr;
                }
                for (Graph<String, String>.Edge edge : graph.listEdges(next)) {
                    String childNode = edge.getChild();
                    if(!(nodePath.containsKey(childNode))) {
                        List<Graph<String, String>.Edge> newPath = new ArrayList<>(curr);
                        newPath.add(edge);
                        nodePath.put(childNode, newPath);
                        nodeQueue.add(childNode);
                    }
                }
            }
            // if none exists
            return null;
        }
    }
}
