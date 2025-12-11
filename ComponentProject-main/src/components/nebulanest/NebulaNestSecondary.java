package components.nebulanest;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.stack.Stack;
import components.stack.Stack1L;
import java.util.Iterator;

/**
 * Layered implementations of secondary methods for NebulaNest.
 * 
 * @author Aayus Keshri
 */
public abstract class NebulaNestSecondary implements NebulaNest {

    // Proximity threshold constant for clustering
    private static final int PROXIMITY_THRESHOLD = 100;

    @Override
    public final void nestCluster(Set<Node> affinities) {

        // If the nest or affinities is empty there's nothing to cluster
        if (!this.isEmpty() && affinities.size() > 0) {

            // Stack to traverse through tree
            Stack<Node> toProcess = new Stack1L<>();
            toProcess.push(this.getRoot());

            while (toProcess.length() > 0) {

                Node current = toProcess.pop();

                // Check each affinity node against the current node
                Set<Node> toRemove = new Set1L<>();
                for (Node affinity : affinities) {

                    NaturalNumber distSquared = current.distanceToSquared(affinity);

                    if (distSquared.compareTo(new NaturalNumber2(PROXIMITY_THRESHOLD)) <= 0) {
                        // If it's close enough to cluster add as a child
                        this.addNode(affinity, current);
                        toRemove.add(affinity);
                    }
                }

                // Remove clustered nodes from affinities
                Iterator<Node> t = toRemove.iterator();
                while (t.hasNext()) {
                    Node node = t.next();
                    affinities.remove(node);
                }

                // Add children to stack to be processed
                Queue<Node> children = current.getChildren();
                int childCount = children.length();
                for (int i = 0; i < childCount; i++) {
                    Node child = children.dequeue();
                    toProcess.push(child);
                    children.enqueue(child);
                }
            }
        }
    }

    @Override
    public final NaturalNumber queryDensity(Node center) {

        NaturalNumber density = new NaturalNumber2(0);

        if (!this.isEmpty()) {
            // Stack to traverse through tree
            Stack<Node> toProcess = new Stack1L<>();
            toProcess.push(this.getRoot());

            while (toProcess.length() > 0) {
                Node current = toProcess.pop();

                // Check if the current node is within the proximity threshold
                NaturalNumber distSquared = current.distanceToSquared(center);
                if (distSquared.compareTo(new NaturalNumber2(PROXIMITY_THRESHOLD)) <= 0) {
                    density.increment();
                }

                // Add children to stack for processing
                Queue<Node> children = current.getChildren();

                int childCount = children.length();

                for (int i = 0; i < childCount; i++) {
                    Node child = children.dequeue();
                    toProcess.push(child);
                    children.enqueue(child);
                }
            }
        }

        return density;
    }

    @Override
    public final NaturalNumber getSize() {

        NaturalNumber size = new NaturalNumber2(0);

        if (!this.isEmpty()) {
            // Stack to traverse through tree
            Stack<Node> toProcess = new Stack1L<>();
            toProcess.push(this.getRoot());

            while (toProcess.length() > 0) {
                Node current = toProcess.pop();
                size.increment();

                // Add children to stack for processing
                Queue<Node> children = current.getChildren();
                int childCount = children.length();
                for (int i = 0; i < childCount; i++) {

                    Node child = children.dequeue();
                    toProcess.push(child);
                    children.enqueue(child);
                }
            }
        }

        return size;
    }

    @Override
    public final void removeNode(Node target) {

        Node root = this.getRoot();
        boolean targetRemoved = false;

        // If the target is root then clear the entire nest
        if (root.equals(target)) {
            this.clear();
            targetRemoved = true;
        }

        // Find and remove target node
        if (!targetRemoved) {
            Stack<Node> toProcess = new Stack1L<>();
            toProcess.push(root);

            while (toProcess.length() > 0 && !targetRemoved) {

                Node current = toProcess.pop();
                Queue<Node> children = current.getChildren();
                Queue<Node> temp = new Queue1L<>();
                boolean found = false;

                int childCount = children.length();

                for (int i = 0; i < childCount; i++) {
                    Node child = children.dequeue();

                    if (child.equals(target)) {
                        // If target is found don't add it back to the queue
                        found = true;
                        targetRemoved = true;
                    } else {
                        temp.enqueue(child);

                        if (!found) {
                            toProcess.push(child);
                        }
                    }
                }

                // Restores children queue
                children.transferFrom(temp);
            }
        }
    }

    /*
     * Common methods
     */

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();

        if (this.isEmpty()) {
            sb.append("NebulaNest[]");
        } else {
            sb.append("NebulaNest[\n");

            // Use stack to traverse
            Stack<Node> nodeStack = new Stack1L<>();
            Stack<Integer> depthStack = new Stack1L<>();

            nodeStack.push(this.getRoot());
            depthStack.push(0);

            while (nodeStack.length() > 0) {
                Node current = nodeStack.pop();
                int depth = depthStack.pop();

                // Add indentation
                for (int i = 0; i < depth; i++) {
                    sb.append("  ");
                }

                // Add current node
                sb.append("Node(").append(current.getX()).append(", ").append(current.getY()).append(")\n");

                // Adds children to stack in reverse so that it displays correctly
                Queue<Node> children = current.getChildren();
                Stack<Node> childrenReversed = new Stack1L<>();
                int childCount = children.length();

                for (int i = 0; i < childCount; i++) {
                    Node child = children.dequeue();
                    childrenReversed.push(child);
                    children.enqueue(child);
                }

                while (childrenReversed.length() > 0) {
                    nodeStack.push(childrenReversed.pop());
                    depthStack.push(depth + 1);
                }
            }

            sb.append("]");
        }

        return sb.toString();
    }

    @Override
    public final boolean equals(Object obj) {
        boolean result = false;

        if (this == obj) {
            result = true;
        } else if (obj != null && obj instanceof NebulaNest) {
            NebulaNest other = (NebulaNest) obj;

            // Both are empty
            if (this.isEmpty() && other.isEmpty()) {
                result = true;
            } else if (!this.isEmpty() && !other.isEmpty()) {
                // Use comparison with two stacks
                Stack<Node> stack1 = new Stack1L<>();
                Stack<Node> stack2 = new Stack1L<>();
                boolean equal = true;

                stack1.push(this.getRoot());
                stack2.push(other.getRoot());

                while (stack1.length() > 0 && stack2.length() > 0 && equal) {
                    Node node1 = stack1.pop();
                    Node node2 = stack2.pop();

                    // Compares coordinates
                    if (node1.getX() != node2.getX() || node1.getY() != node2.getY()) {
                        equal = false;
                    }

                    // Compares children count
                    if (equal) {
                        Queue<Node> children1 = node1.getChildren();
                        Queue<Node> children2 = node2.getChildren();

                        if (children1.length() != children2.length()) {
                            equal = false;
                        } else {

                            // Adds children to stacks for comparison
                            int childCount = children1.length();
                            for (int i = 0; i < childCount; i++) {
                                Node child1 = children1.dequeue();
                                Node child2 = children2.dequeue();

                                stack1.push(child1);
                                stack2.push(child2);
                                children1.enqueue(child1);
                                children2.enqueue(child2);
                            }
                        }
                    }
                }

                result = equal && stack1.length() == 0 && stack2.length() == 0;
            }
        }

        return result;
    }

}