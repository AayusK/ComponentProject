package components.nebulanest;

import components.naturalnumber.NaturalNumber;
import components.set.Set;

/*
 * Enhanced interface for NebulaNestKernel adds high level operations for clustering, 
 * querying, and managing node densities
 * 
 * @author Aayus Keshri
 */
public interface NebulaNest extends NebulaNestKernel {
    /*
     * Clusters nodes from the given set of affinities into subtrees based on
     * proximity.
     */
    void nestCluster(Set<Node> affinities);

    /*
     * Finds the density of nodes within a proximity threshold of the center node.
     */
    NaturalNumber queryDensity(Node center);

    /*
     * Computes the total number of nodes in the tree
     */
    NaturalNumber getSize();

    /*
     * Removes the given node and its entire subtree from the nest
     */
    void removeNode(Node target);
}