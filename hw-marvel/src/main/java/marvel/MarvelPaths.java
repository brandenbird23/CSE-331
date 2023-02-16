package marvel;
import graph.*;

import java.util.*;

/**
 * This class will build a labeled graph that consists of
 * characters as nodes and edges between the nodes that
 * represent the comic book they share. Will find the shortest
 * path.
 */
public class MarvelPaths {
    /* Not an ADT */

    /**
     * This method asks the user for input of the marvel file to be used. It then allows
     * for the user to input two marvel characters to see if they are linked between comic
     * books. At the end, it will ask the user if they want to continue and search again
     * or exit the program. If a path is found, it will display so, if none is found, it
     * will also display so.
     *
     * @param args main method
     */
    public static void main(String[] args) {
        Graph<String, String> marvelGraph = MarvelPaths.graphCreator("marvel.csv");
        Scanner input = new Scanner(System.in);
        Scanner answer = new Scanner(System.in);
        boolean again = true;
        String char1, char2;
        while (again) {
            System.out.println("Here you can find connections of two marvel characters by comic books!");
            System.out.println("Please type the name of the first character: ");
            char1 = input.nextLine().toUpperCase();
            System.out.println("Please type the name of the second character: ");
            char2 = input.nextLine().toUpperCase();

            if (!(marvelGraph.containsNode(char1))) {
                System.out.println(char1 + " is an unknown character");
            } else if (!(marvelGraph.containsNode(char2))) {
                System.out.println(char2 + " is an unknown character");
            } else {
                System.out.println();
                System.out.println("Searching for " + char1 + " and " + char2 + " paths");
                List<Graph<String, String>.Edge> path = findPath(marvelGraph, char1, char2);
                if (path == null) {
                    System.out.println("Path from " + char1 + " to " + char2 + " was not found.");
                } else {
                    // string to set new parent node after each iteration
                    String parent = char1;
                    for (Graph<String, String>.Edge edge : path) {
                        System.out.println(edge.getParent() + " to " + edge.getChild() +
                                " via " + edge.getLabel());
                        parent = edge.getChild();
                    }
                }
            }
            System.out.println();
            System.out.println("Would you like to search again? \n" +
                    "Enter (y) to continue or enter (n) to exit");
            String decision = answer.nextLine().toLowerCase();
            if (decision.charAt(0) == 'y') {
                again = true;
            } else if (decision.charAt(0) == 'n') {
                again = false;
            } else {
                System.out.println("Please use (y/n)");
            }
        }
        System.out.println("Have a nice day!");
        input.close();
        answer.close();
    }

    /**
     * Builds a graph using the file given as argument. The file given must not be null.
     *
     * @param fileName the file given that the graph builds from
     * @return a new marvel graph
     * @throws IllegalArgumentException fileName != null
     */
    public static Graph<String, String> graphCreator(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        } else {

            Map<String, List<String>> comics = MarvelParser.parseData(fileName);
            Graph<String, String> marvelGraph = new Graph<>();

            for (String comic : comics.keySet()) {
                List<String> charsInComic = comics.get(comic);
                for (int i = 0; i < charsInComic.size(); i++) {
                    String parent = charsInComic.get(i);
                    // Check if the parent node already exists in the graph
                    if (!(marvelGraph.containsNode(parent))) {
                        marvelGraph.addNode(parent);
                    }
                    for (int j = i + 1; j < charsInComic.size(); j++) {
                        String child = charsInComic.get(j);
                        if (!child.isEmpty() && !marvelGraph.containsNode(child)) {
                            marvelGraph.addNode(child);
                        }
                        marvelGraph.addEdge(parent, child, comic);
                        marvelGraph.addEdge(child, parent, comic);
                    }
                }
            }
            return marvelGraph;
        }
    }

    /**
     * Finds the shortest path between characters using breadth-first search.
     * Will return the shortest path between the char1 and char2. WIll be
     * in lexicographical order.
     *
     * @param graph the graph being searched to find the shortest path
     * @param char1 the starting character (node)
     * @param char2 the ending character (node)
     * @return the shortest path between characters (char1 and char2)
     * @throws IllegalArgumentException if char1 or char2 is not in the graph or graph is null
     */
    public static List<Graph<String, String>.Edge> findPath(Graph<String, String> graph, String char1, String char2) {
        // Check for null or invalid input
        if (graph == null || char1 == null || char2 == null) {
            throw new IllegalArgumentException("Invalid input: graph, char1, and end cannot be null");
        }
        if (!(graph.containsNode(char1))) {
            throw new IllegalArgumentException("Start node not found in graph: " + char1);
        }
        if (!(graph.containsNode(char2))) {
            throw new IllegalArgumentException("End node not found in graph: " + char2);
        }
        Queue<String> nodeQueue = new LinkedList<>();
        // Create a map to store the shortest path to each node
        Map<String, List<Graph<String, String>.Edge>> nodePath = new TreeMap<>();
        // Initialize the shortest path for the char1 node
        List<Graph<String, String>.Edge> startPath = new ArrayList<>();
        nodePath.put(char1, startPath);
        // Add the char1 node to the queue
        nodeQueue.offer(char1);
        // While the queue is not empty, process the next node in the queue
        while (!nodeQueue.isEmpty()) {
            String currNode = nodeQueue.poll();
            List<Graph<String, String>.Edge> currPath = nodePath.get(currNode);
            // If we have reached the end node, return the shortest path
            if (currNode.equals(char2)) {
                return currPath;
            }
            // Iterate over the edges of the current node, sorted lexicographically by label
            List<Graph<String, String>.Edge> sortedEdges = new ArrayList<>(graph.listEdges(currNode));
            // lambda expression takes two edges and compares them
            sortedEdges.sort((e1, e2) -> {
                // compares the child vertices of e1 to the child of e2
                // if e1 < e2, result will be negative, if equal will be 0, and if e1 > e2 result will be positive
                int compare = e1.getChild().compareTo(e2.getChild());
                if (compare != 0) {
                    return compare;
                }
                else {
                    // if the vertices are equal, will compare and sort via the label
                    return e1.getLabel().compareTo(e2.getLabel());
                }
            });
            for (Graph<String, String>.Edge edge : sortedEdges) {
                String childNode = edge.getChild();
                // If the child node has not been visited, add it to the queue and update the shortest path
                if (!nodePath.containsKey(childNode)) {
                    List<Graph<String, String>.Edge> newPath = new ArrayList<>(currPath);
                    newPath.add(edge);
                    nodePath.put(childNode, newPath);
                    nodeQueue.offer(childNode);
                }
            }
        }
        // If we reach here, there is no path from char1 to char2
        return null;
    }
}
