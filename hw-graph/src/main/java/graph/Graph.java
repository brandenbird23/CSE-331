package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph is a mutable list of nodes. It consists of vertices and
 * a set of edges.
 */
public class Graph<T, E> {
    private static final boolean DEBUG = false;
    private Map<T, HashSet<Edge>> graph;
    private int totalEdges = 0;

    /**
     * Constructs a new graph
     * @spec.effects this
     */
    public Graph(){
        this.graph = new HashMap<>();
        checkRep();
    }

    /**
     * Adds a node to the graph
     * @param nodeName is the node being added
     * @spec.requires nodeName != null and does not already exist
     * @spec.effects adds a new node to the graph
     * @spec.modifies this
     * @throws IllegalArgumentException if nodeName already exists
     */
    public void addNode(T nodeName) {
        checkRep();
        if (nodeName != null) {
            if(!(this.graph.containsKey(nodeName))) {
                this.graph.put(nodeName, new HashSet<>());
            } else {
                throw new IllegalArgumentException("Node already exists");
            }
        } else {
            throw new IllegalArgumentException("Node cannot be null");
        }
        checkRep();
    }

    /**
     * Adds an edge to the graph from parent to child with a label
     * for the added edge. Parent and child must already exist in the
     * graph and label must not be already created.
     * @param parent is the beginning node
     * @param child is the ending node
     * @param label is the label of the edge
     * @spec.requires parent and child exist in the graph, parent, child,
     * and label != null, label does not already exist.
     * @spec.effects adds a new edge with parent and child node labeled with label
     * @spec.modifies this
     * @throws IllegalArgumentException if graph does not contain either parent or child
     * or if label already exists.
     */
    public void addEdge(T parent, T child, E label) {
        checkRep();
        if (parent == null || child == null || label == null) {
            throw new IllegalArgumentException("Node and label cannot be null");
        } else if (!(this.graph.containsKey(parent))) {
            throw new IllegalArgumentException("Parent node is not found in the graph");
        } else if (!(this.graph.containsKey(child))) {
            throw new IllegalArgumentException("Child node is not found in the graph");
        } else {
            // check if to be added is a duplicate
            Edge edge1 = new Edge(parent, child, label);
            if(this.graph.get(parent).contains(edge1)) {
                throw new IllegalArgumentException("Edge already exists");
            } else {
                this.graph.get(parent).add(edge1);
                this.totalEdges++;
                checkRep();
            }
        }
    }

    /**
     * Returns all the nodes in the graph
     * @return set of Nodes in graph, empty if no nodes
     */
    public Set<T> listNodes() {
        checkRep();
        return new HashSet<T>(this.graph.keySet());
    }

    /**
     * Returns all children of the given node within the graph
     *
     * @param parent is the current node calling for the child
     * @spec.requires parent != null
     * @return set of all children to the parent node, empty if no children
     * @throws IllegalArgumentException if graph does not contain given parent node
     */
    public Set<T> listChildren(T parent) {
        // TODO [create method]
        throw new RuntimeException("listChildren has not been implemented");
    }

    /**
     * Returns all edges of the given node within the graph
     * @spec.requires node != null
     * @param node to get all the Edges from
     * @return set if all edges to the given node, empty if no edges
     * @throws IllegalArgumentException if graph does not contain given node
     */
    public Set<Edge> listEdges(T node) {
        // TODO [create method]
        throw new RuntimeException("listEdges has not been implemented");
    }

    /**
     * Returns the number of edges between the parent node and the child node
     * @param parent is the starting Node
     * @param child is the ending Node
     * @spec.requires parent, child != null
     * @return the num of edges between parent and child node
     */
    public int totalNumEdges(T parent, T child) {
        // TODO [create method]
        throw new RuntimeException("totalNumEdges has not been implemented");
    }

    /**
     * Returns the total number of nodes in the graph
     * @return the number of nodes in the graph
     */
    public int totalNodes() {
        // TODO [create method]
        throw new RuntimeException("totalNodes has not been implemented");
    }

    /**
     * Returns the total number of edges in the graph
     * @return the number of edges in the graph
     */
    public int totalEdges() {
        // TODO [create method]
        throw new RuntimeException("totalEdges has not been implemented");
    }


    private void checkRep() {
        assert (this.graph != null) : "graph can't be null";
        assert (!(this.graph.containsKey(null)));
        if (DEBUG) {
            for (T node : this.graph.keySet()) assert (node != null) : "nodes cannot be null";
            for (T node : this.graph.keySet()){
                HashSet<Edge> edgeSet = this.graph.get(node);
                assert (this.graph.get(node) != null) : "nodes must not have null edges";
                for (Edge edge : edgeSet) {
                    assert (edge != null) : "graph cannot have null edges";
                    assert (graph.containsKey(edge.getChild())) : "graph must have child node";
                }
            }
        }
    }

    /**
     * Returns if graph is empty or not
     * @return true if graph has no nodes, false if has nodes
     */
    public boolean isEmpty() {
        checkRep();
        return this.graph.isEmpty();
    }

    /**
     * Clears the graph of all nodes and edges
     * @spec.modifies this
     */
    public void clear() {
        checkRep();
        this.graph.clear();
        checkRep();
    }


    /**
     * This class represents a single, immutable edge. An edge points to
     * an end node from starter node. Edges also store the label.
     */
    public class Edge {
        /**
         * Create edge with parent and child node and a label
         * @param parent starting node
         * @param child ending node
         * @param label label of the edge
         * @spec.requires child, label != null
         * @throws IllegalArgumentException if null
         */
        public Edge(T parent, T child, E label) {
            // TODO [create method]
            throw new RuntimeException("Edge has not been implemented");
        }

        /**
         * Return the parent of the edge
         * @return parent of edge
         */
        public T getParent() {
            // TODO [create method]
            throw new RuntimeException("getParent has not been implemented");
        }

        /**
         * Return the child of the edge
         * @return child of edge
         */
        public T getChild() {
            // TODO [create method]
            throw new RuntimeException("getChild has not been implemented");
        }

        /**
         * Returns the label of the edge
         * @return label of edge
         */
        public E getLabel() {
            // TODO [create method]
            throw new RuntimeException("getLabel has not been implemented");
        }
    }
}