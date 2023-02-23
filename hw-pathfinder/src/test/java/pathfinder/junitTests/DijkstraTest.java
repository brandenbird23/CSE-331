package pathfinder.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.Dijkstra;

public class DijkstraTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // Testing Graph
    private Graph<String, Double> graph1;
    // Empty graph
    private Graph<String, Double> graph2;

    @Before
    public void begin() {
        this.graph1 = new Graph<>();
        this.graph2 = new Graph<>();

        this.graph1.addNode("A");
        this.graph1.addNode("B");
        this.graph1.addNode("C");
        this.graph1.addNode("D");

        this.graph1.addEdge("A", "B", 1.0);
        this.graph1.addEdge("A", "B", 10.0);
        this.graph1.addEdge("B", "C", 1.0);
        this.graph1.addEdge("B", "C", 10.0);
        this.graph1.addEdge("C", "D", 5.0);
        this.graph1.addEdge("B", "D", 8.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullGraph() {
        Dijkstra.findPath(graph2, "A", "B");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullStart() {
        Dijkstra.findPath(graph1, null, "A");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNullEnd() {
        Dijkstra.findPath(graph1, "A", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testStartNotInGraph(){
        Dijkstra.findPath(graph1, "E", "A");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testEndNotInGraph(){
        Dijkstra.findPath(graph1, "A", "E");
    }
}
