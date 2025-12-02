import components.standard.Standard;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Kernel implementation of NebulaNest using a simple tree structure.
 * 
 * @convention
 * [root is the root node of the tree]
 * [if root is null, the nest is empty]
 * [all nodes in the tree are reachable from root
 * @correspondence
 * this = [the tree rooted at root]
 * 
 * @author Aayus Keshri
 */
public class NebulaNest1L extends NebulaNestSecondary {

    /*
     * Private members
     */


    /**
     * Root of the tree.
     */
    private Node root;


    /**
     * Create initial representation.
     */
    private void createNewRep() {
        this.root = null;
    }

    /*
     * Constructors
     */

    /**
     * No-arg constructor.
     */
    public NebulaNest1L() {
        this.createNewRep();
    }

    /*
     * Standard methods
     */

    @Override
    public final NebulaNest newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NebulaNest source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NebulaNest1L : ""
                + "Violation of: source is of dynamic type NebulaNest1L";
        NebulaNest1L localSource = (NebulaNest1L) source;
        this.root = localSource.root;
        localSource.createNewRep();
    }

    /*
     * Kernel methods
     */

    @Override
    public final boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public final void addNode(Node newNode, Node parent) {
        assert newNode != null : "Violation of: newNode is not null";
        if (parent == null) {
            assert this.isEmpty() : "Violation of: parent is null implies this is empty";
            this.root = newNode;
        } else {            
            Queue<Node> children = parent.getChildren();
            children.enqueue(newNode);
        }
    }

    @Override
    public final Node getRoot() {
        assert !this.isEmpty() : "Violation of: this is not empty";
        return this.root;
    }

}
