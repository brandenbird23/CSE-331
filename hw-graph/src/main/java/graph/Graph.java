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
    private static final boolean DEBUG = true;
    private Map<T, HashSet<Edge>> graph;
    private int totalEdges = 0;

    /**
     * Constructs a new graph
     * @spec.effects this
     */
    public Graph(){
        this.graph = new HashMap<>();
        if (DEBUG) {
            checkRep();
        }
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
        if (DEBUG) {
            checkRep();
        }
        if (nodeName != null) {
            if(!(this.graph.containsKey(nodeName))) {
                this.graph.put(nodeName, new HashSet<>());
            } else {
                throw new IllegalArgumentException("Node already exists");
            }
        } else {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (DEBUG) {
            checkRep();
        }
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
        if (DEBUG) {
            checkRep();
        }
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
            if (DEBUG) {
                checkRep();
            }
        }
    }

    /**
     * Returns all the nodes in the graph
     * @return set of Nodes in graph, empty if no nodes
     */
    public Set<T> listNodes() {
        if (DEBUG) {
            checkRep();
        }
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
        if (DEBUG) {
            checkRep();
        }
        if(!(this.graph.containsKey(parent))) {
            throw new IllegalArgumentException("The given node is not found");
        } else {
            Set<T> child = new HashSet<>();
            for (Edge edge : this.graph.get(parent)) {
                child.add(edge.getChild());
            }
            if (DEBUG) {
                checkRep();
            }
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
        if (DEBUG) {
            checkRep();
        }
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        } else if (!(this.graph.containsKey(node))) {
            throw new IllegalArgumentException("Graph does not contain the given node");
        } else {
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
        if (DEBUG) {
            checkRep();
        }
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
            if (DEBUG) {
                checkRep();
            }
            return counter;
        }
    }

    /**
     * Returns the total number of nodes in the graph
     * @return the number of nodes in the graph
     */
    public int totalNodes() {
        if (DEBUG) {
            checkRep();
        }
        return this.graph.size();
    }

    /**
     * Returns the total number of edges in the graph
     * @return the number of edges in the graph
     */
    public int totalEdges() {
        if (DEBUG) {
            checkRep();
        }
        return this.totalEdges;
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
        if (DEBUG) {
            checkRep();
        }
        return this.graph.isEmpty();
    }

    /**
     * Clears the graph of all nodes and edges
     * @spec.modifies this
     */
    public void clear() {
        if (DEBUG) {
            checkRep();
        }
        this.graph.clear();
        if (DEBUG) {
            checkRep();
        }
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
            if (DEBUG) {
                checkRep();
            }
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
            if (DEBUG) {
                checkRep();
            }
            return this.label;
        }
    }
}