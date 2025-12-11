import components.nebulanest.BasicNode;
import components.nebulanest.NebulaNest;
import components.nebulanest.NebulaNest1L;
import components.nebulanest.NebulaNestKernel.Node;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Simple demo of NebulaNest functionality.
 */
public final class NebulaNestDemo {

    private NebulaNestDemo() {
    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        out.println("Initializing NebulaNest");
        NebulaNest nest = new NebulaNest1L();

        // Creates root node at (0,0)
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);
        out.println("Root added at (0,0).");

        // Adds children
        Node child1 = new BasicNode(10, 10);
        nest.addNode(child1, root);
        out.println("Child added at (10,10).");

        Node child2 = new BasicNode(5, 5);
        nest.addNode(child2, root);
        out.println("Child added at (5,5).");

        out.println("Nest structure:");
        out.println(nest.toString());

        out.println("Nest size: " + nest.getSize());

        out.println("Querying density around root (0,0):");
        out.println(nest.queryDensity(root));

        out.close();
    }
}
