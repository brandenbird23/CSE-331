package graph.junitTests;

import graph.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.*;
import static org.junit.Assert.*;

/**
 * This class tests
 */
public class GraphTests {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested
    private final Graph<String, String> graph1 = new Graph<>();


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGraph() {
        assertTrue(graph1.isEmpty());
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addNode
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddNode() {
        graph1.addNode("node1");
        assertNotNull(graph1);
        assertEquals(Arrays.asList("node1"), graph1.listNodes());
    }

    @Test
    public void testAddMulNodes() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        assertNotNull(graph1);
        assertEquals(Arrays.asList("node1", "node2"),
                graph1.listNodes());
    }

    @Test (expected = RuntimeException.class)
    public void testDuplicateNodes() {
        graph1.addNode("node1");
        graph1.addNode("node1");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullNode() {
        graph1.addNode(null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addEdge
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddEdge() {
        graph1.addNode("parent");
        graph1.addNode("child");
        graph1.addEdge("parent", "child", "label");
        assertNotNull(graph1);
        assertEquals(Arrays.asList("parent", "child", "label"),
                graph1.listChildren("parent"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeNoParent() {
        graph1.addNode("node2");
        graph1.addEdge(null, "node2", "label");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeNoChild() {
        graph1.addNode("node1");
        graph1.addEdge("node1", null, "label");
    }

    @Test
    public void testAddMulEdge() {
        graph1.addNode("parent");
        graph1.addNode("child");
        graph1.addEdge("parent", "child", "label1");
        graph1.addEdge("parent", "child", "label2");
        assertNotNull(graph1);
        assertEquals(Arrays.asList("parent", "child", "label"),
                graph1.listChildren("parent"));
    }

    @Test (expected = RuntimeException.class)
    public void testDuplicateEdge() {
        graph1.addNode("node1");
        graph1.addNode("node2");

        graph1.addEdge("node1", "node2", "label");
        graph1.addEdge("node1", "node2", "label");
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  totals
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testTotalNodes() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        graph1.addNode("node3");
        assertNotNull(graph1);
        assertEquals(3, graph1.totalNodes());
    }

    @Test
    public void testTotalEdges() {
        graph1.addNode("node1");
        graph1.addNode("node2");

        graph1.addEdge("node1", "node2", "label1");
        graph1.addEdge("node2", "node1", "label2");
        assertNotNull(graph1);
        assertEquals(2, graph1.totalEdges());
    }

    @Test
    public void testTotalNumEdges() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        graph1.addEdge("node1", "node2", "label1");
        graph1.addEdge("node1", "node2", "label2");
        assertNotNull(graph1);
        assertEquals(2, graph1.totalNumEdges("node1", "node2"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  clear and empty
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testClear() {
        graph1.addNode("node1");
        graph1.addNode("node2");
        graph1.addNode("node3");
        graph1.addEdge("node1", "node2", "label1");
        graph1.addEdge("node1", "node2", "label2");

        graph1.clear();
        assertTrue(graph1.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        graph1.addNode("node1");
        assertFalse(graph1.isEmpty());
    }

}