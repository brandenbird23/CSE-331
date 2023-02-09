package graph.junitTests;

import graph.Graph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        assertTrue(this.graph1.isEmpty());
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addNode
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddNode() {
        this.graph1.addNode("node1");
        assertNotNull(this.graph1);
        Set<String> node = this.graph1.listNodes();
        Set<String> expected = new HashSet<>();
        expected.add("node1");
        assertEquals(expected, node);
    }

    @Test
    public void testAddMulNodes() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");
        assertNotNull(this.graph1);
        Set<String> nodes = this.graph1.listNodes();
        Set<String> expected = new HashSet<>();
        expected.add("node1");
        expected.add("node2");
        assertEquals(expected, nodes);
    }

    @Test (expected = RuntimeException.class)
    public void testDuplicateNodes() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node1");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddNullNode() {
        this.graph1.addNode(null);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  addEdge
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddEdge() {
        this.graph1.addNode("parent");
        this.graph1.addNode("child");
        this.graph1.addEdge("parent", "child", "label");
        assertNotNull(this.graph1);
        Set<String> children = this.graph1.listChildren("parent");
        Set<String> expected = new HashSet<>();
        expected.add("child");
        assertEquals(expected, children);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeNoParent() {
        this.graph1.addNode("node2");
        this.graph1.addEdge(null, "node2", "label");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddEdgeNoChild() {
        this.graph1.addNode("node1");
        this.graph1.addEdge("node1", null, "label");
    }

    @Test
    public void testAddMulEdge() {
        this.graph1.addNode("parent");
        this.graph1.addNode("child");
        this.graph1.addEdge("parent", "child", "label1");
        this.graph1.addEdge("parent", "child", "label2");
        assertNotNull(this.graph1);
        Set<String> children = this.graph1.listChildren("parent");
        Set<String> expected = new HashSet<>(Arrays.asList("child"));
        assertEquals(expected, children);
    }

    @Test (expected = RuntimeException.class)
    public void testDuplicateEdge() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");

        this.graph1.addEdge("node1", "node2", "label");
        this.graph1.addEdge("node1", "node2", "label");
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  totals
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testTotalNodes() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");
        this.graph1.addNode("node3");
        assertNotNull(this.graph1);
        assertEquals(3, this.graph1.totalNodes());
    }

    @Test
    public void testTotalEdges() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");

        this.graph1.addEdge("node1", "node2", "label1");
        this.graph1.addEdge("node2", "node1", "label2");
        assertNotNull(this.graph1);
        assertEquals(2, this.graph1.totalEdges());
    }

    @Test
    public void testTotalNumEdges() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");
        this.graph1.addEdge("node1", "node2", "label1");
        this.graph1.addEdge("node1", "node2", "label2");
        assertNotNull(this.graph1);
        assertEquals(2, this.graph1.totalNumEdges("node1", "node2"));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  clear and empty
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testClear() {
        this.graph1.addNode("node1");
        this.graph1.addNode("node2");
        this.graph1.addNode("node3");
        this.graph1.addEdge("node1", "node2", "label1");
        this.graph1.addEdge("node1", "node2", "label2");

        this.graph1.clear();
        assertTrue(this.graph1.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        this.graph1.addNode("node1");
        assertFalse(this.graph1.isEmpty());
    }

}