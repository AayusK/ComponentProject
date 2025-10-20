import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;

public final class NebulaNest {

    private static final int PROXIMITY_THRESHOLD = 5;

    public static class Node {
        private int x;
        private int y;
        private Queue<Node> children;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.children = new Queue1L<>();
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public Queue<Node> getChildren() {
            return this.children;
        }

        public double distanceTo(Node other) {
            int dx = this.x - other.x;
            int dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + ")";
        }
    }

    
     // Root node of the tree
    private Node root;

    
    // Constructor - creates an empty NebulaNest.
    public NebulaNest() {
        this.root = null;
    }

    //                   KERNEL METHODS 

    /**
     * Adds a new node as a child of the parent and updates connections
     */
    public void addNode(Node newNode, Node parent) {
        if (parent == null) {
            this.root = newNode;
        } else {
            parent.getChildren().enqueue(newNode);
        }
    }

    /**
     * Reports if the nest has no nodes
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Returns the root node of the nest
     */
    public Node getRoot() {
        return this.root;
    }

    //                     SECONDARY METHODS

    /**
     * Groups nodes by proximity using traversal.
     */
    public void nestCluster(Set<Node> affinities) {
        if (this.isEmpty() || affinities.size() == 0) {
            return;
        }

        Node root = this.getRoot();

        while (affinities.size() > 0) {
            Node center = affinities.removeAny();

            Queue<Node> toProcess = new Queue1L<>();
            toProcess.enqueue(root);

            while (toProcess.length() > 0) {
                Node current = toProcess.dequeue();

                if (current.distanceTo(center) <= PROXIMITY_THRESHOLD) {
                    Node newNode = new Node(current.getX(), current.getY());
                    this.addNode(newNode, center);
                }

                Queue<Node> children = current.getChildren();
                int childCount = children.length();
                for (int i = 0; i < childCount; i++) {
                    Node child = children.dequeue();
                    toProcess.enqueue(child);
                children.enqueue(child);
                }
            }
        }
    }

    /**
     * Computes and returns the density of data points around the center node.
     */
    public NaturalNumber queryDensity(Node center) {
        NaturalNumber density = new NaturalNumber2(0);
        if (this.isEmpty() || center == null) {
            return density;
        }

        Node root = this.getRoot();
        Queue<Node> toProcess = new Queue1L<>();
        toProcess.enqueue(root);

        while (toProcess.length() > 0) {
            Node current = toProcess.dequeue();

            if (current.distanceTo(center) <= PROXIMITY_THRESHOLD) {
                density.increment();
            }

            Queue<Node> children = current.getChildren();
            int childCount = children.length();
            for (int i = 0; i < childCount; i++) {
                Node child = children.dequeue();
                toProcess.enqueue(child);
                children.enqueue(child);
            }
        }

        return density;
}

    /**
     * Main method to demonstrate NebulaNest functionality.
     */
    public static void main(String[] args) {
        NebulaNest nest = new NebulaNest();

        // Test isEmpty
        System.out.println("Is nest empty? " + nest.isEmpty());

        // Create and add root
        Node root = new Node(0, 0);
        nest.addNode(root, null);
        System.out.println("Added root: " + root);

        // Get root
        System.out.println("Get root: " + nest.getRoot());
        System.out.println("Is nest empty now? " + nest.isEmpty());

        // Add some nodes
        Node n1 = new Node(2, 3);
        Node n2 = new Node(1, 1);

        nest.addNode(n1, root);
        nest.addNode(n2, root);

        System.out.println("Added nodes: " + n1 + ", " + n2);
    }
}