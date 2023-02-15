package marvel;
import graph.*;

import java.util.*;


public class MarvelPaths {
    /* Not an ADT */

    /**
     * This method asks the user for input of the marvel file to be used. It then allows
     * for the user to input two marvel characters to see if they are linked between comic
     * books. At the end, it will ask the user if they want to continue and search again
     * or exit the program. If a path is found, it will display so, if none is found, it
     * will also display so.
     * @param args
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
                List<Graph<String, String>.Edge> path = BFS(marvelGraph, char1, char2);
                if (path == null) {
                    System.out.println("Path from" + char1 + " to " + char2 + " was not found.");
                } else {
                    for (Graph<String, String>.Edge edge : path) {
                        System.out.println(edge.getParent() + " and " + edge.getChild() +
                                " are in " + edge.getLabel());
                    }
                }
            }

            System.out.println();
            System.out.println("Would you like to search again? \n" +
                    "Enter (y) to continue or enter (n) to exit");
            String decision = answer.nextLine().toLowerCase();
            if(decision.charAt(0) == 'y') {
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
