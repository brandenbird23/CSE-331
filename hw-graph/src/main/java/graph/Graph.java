package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Graph is a mutable list of nodes. It consists of vertices and
 * a set of edges. The nodes are represented by type T and the edges
 * are represented by type E. The edges connect the nodes together.
 * A graph cannot contain duplicate nodes. Also, no two edges that share
 * a parent and child node will have the same label, they will be different.
 * The nodes cannot be null and edges for a given node cannot be null. Edges
 * can start and end at the same node.
 */
public class Graph<T, E> {
    private static final boolean DEBUG = false;
    private Map<T, HashSet<Edge>> graph;
    private int totalEdges = 0;

    // Rep Invariant:
    // graph != null
    // every node and every edge != null
    // no duplicate nodes
    // graph must contain node if node is included in any edge of the graph
    //
    // Abstract Function:
    // Graph g represents a map of nodes. each node has a set of edges that
    // connect them to other nodes within the graph. the nodes are mapped as keys
    // in a hashmap and their edges are stored as a hashset of edge objects in values.

    /**
     * Constructs a new graph
     * @spec.effects constructs a new empty graph
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
     * @spec.modifies this graph by adding a node
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
     * @spec.modifies this graph by adding an edge with nodes
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
            Set<Edge> edges = this.graph.get(parent);
            for (Edge edge : edges) {
                if (edge.getChild().equals(child) && edge.getLabel().equals(label)
                && edge.getParent().equals(parent)) {
                    throw new IllegalArgumentException("Edge already exists");
                }
            }
            Edge edge = new Edge(parent, child, label);
            this.graph.get(parent).add(edge);
            this.totalEdges++;
            checkRep();
        }
    }

    /**
     * Returns all the nodes in the graph
     * @return set of Nodes in graph, empty if no nodes
     */
    public Set<T> listNodes() {
        checkRep();
        return new HashSet<>(this.graph.keySet());
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
        checkRep();
        if(!(this.graph.containsKey(parent))) {
            throw new IllegalArgumentException("The given node is not found");
        } else {
            Set<T> child = new HashSet<>();
            for (Edge edge : this.graph.get(parent)) {
                child.add(edge.getChild());
            }
            checkRep();
            return child;
        }
    }

    /**
     * Returns all edges of the given node within the graph
     * @spec.requires node != null
     * @param node to get all the Edges from
     * @return set if all edges to the given node, empty if no edges
     * @throws IllegalArgumentException if graph does not contain given node
     */
    public Set<Edge> listEdges(T node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        } else if (!(this.graph.containsKey(node))) {
            throw new IllegalArgumentException("Graph does not contain the given node");
        } else {
            checkRep();
            return new HashSet<>(this.graph.get(node));
        }
    }

    /**
     * Returns the number of edges between the parent node and the child node
     * @param parent is the starting Node
     * @param child is the ending Node
     * @spec.requires parent, child != null
     * @return the num of edges between parent and child node
     */
    public int totalNumEdges(T parent, T child) {
        checkRep();
        if (parent == null || child == null) {
            throw new IllegalArgumentException("A given node cannot be null");
        } else if (!(this.graph.containsKey(parent))) {
            throw new IllegalArgumentException("Parent node was not found in the graph");
        } else if (!(this.graph.containsKey(child))) {
            throw new IllegalArgumentException("Child node was not found in the graph");
        } else {
            int counter = 0;
            for (Edge edge : this.graph.get(parent)) {
                if (edge.getChild().equals(child)) {
                    counter++;
                }
            }
            checkRep();
            return counter;
        }
    }

    /**
     * Returns the total number of nodes in the graph
     * @return the number of nodes in the graph
     */
    public int totalNodes() {
        checkRep();

        return this.graph.size();
    }

    /**
     * Returns the total number of edges in the graph
     * @return the number of edges in the graph
     */
    public int totalEdges() {
        checkRep();
        return this.totalEdges;
    }

    /**
     * Retrieves the labels for the parent and child nodes.
     * @param parent node for parent label
     * @param child node for child label
     * @return a set of labels representing the edge between the parent and child
     * @spec.requires both the parent and child are in the graph
     */
    public Set<E> getLabel(T parent, T child){
        checkRep();
        Set<E> labels = new HashSet<>();
        for (Edge edge : graph.get(parent)) {
            if (child.equals(edge.getChild())) {
                labels.add(edge.getLabel());
            }
        }
        checkRep();
        return labels;
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
     * @return true if graph has no nodes, false if graph has nodes
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
     * Checks if a given node is inside the graph
     * @param node node to see if is in the graph
     * @spec.requires node != null
     * @return true if node is in the graph, false if not in the graph
     * @throws IllegalArgumentException if node is null
     */
    public boolean containsNode(T node) {
        checkRep();
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        return graph.containsKey(node);
    }


    /**
     * This class represents a single, immutable edge. An edge points to
     * an end node from starter node. Edges also store the label.
     */
    public class Edge {
        private T parent;
        private T child;
        private E label;
        /**
         * Create edge with parent and child node and a label
         * @param parent starting node
         * @param child ending node
         * @param label label of the edge
         * @spec.requires child, parent, label != null
         * @throws IllegalArgumentException if null
         */
        public Edge(T parent, T child, E label) {
            if (parent == null) {
                throw new IllegalArgumentException("The parent cannot be null");
            } else if (child == null) {
                throw new IllegalArgumentException("The child cannot be null");
            } else if (label == null) {
                throw new IllegalArgumentException("The label cannot be null");
            } else {
                this.parent = parent;
                this.child = child;
                this.label = label;
            }
            checkRep();
        }

        /**
         * Return the parent of the edge
         * @return parent of edge
         */
        public T getParent() {
            return this.parent;
        }

        /**
         * Return the child of the edge
         * @return child of edge
         */
        public T getChild() {
            return this.child;
        }

        /**
         * Returns the label of the edge
         * @return label of edge
         */
        public E getLabel() {
            checkRep();
            return this.label;
        }
    }
}