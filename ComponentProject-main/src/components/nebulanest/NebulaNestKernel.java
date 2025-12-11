package components.nebulanest;

import components.naturalnumber.NaturalNumber;
import components.queue.Queue;
import components.standard.Standard;

/**
 * Kernel interace for NebulaNest, a mutable tree structure for organizing nodes
 * by proximity
 * 
 * @author Aayus Keshri
 */
public interface NebulaNestKernel extends Standard<NebulaNest> {
    /**
     * Kernel interface for a node in the NebulaNest tree
     */
    public interface Node {
        /**
         * Retrieves the x coordinate of the node
         */
        int getX();

        /*
         * Retrieves the y coordinate of the node
         */
        int getY();

        /*
         * Retrieves the queue of children nodes
         */
        Queue<Node> getChildren();

        /*
         * Calculates the euclidean distance to another node
         */
        NaturalNumber distanceToSquared(Node other);
    }

    /*
     * Checks if the NebulaNest is empty
     */
    boolean isEmpty();

    /*
     * Adds a new node as a child of the parent specified
     */
    void addNode(Node newNode, Node parent);

    /*
     * Retrieves the root node of the nest
     */
    Node getRoot();

    /*
     * Clears all nodes from the nest
     */
    void clear();
}
