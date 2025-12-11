import components.nebulanest.BasicNode;
import components.nebulanest.NebulaNest;
import components.nebulanest.NebulaNest1L;
import components.nebulanest.NebulaNestKernel.Node;
import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Advanced use case simulating a Star Map that clusters stars based on
 * proximity.
 */
public final class StarMap {

    private StarMap() {
    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        out.println("Generating Star Map...");
        NebulaNest starMap = new NebulaNest1L();

        // Galactic Center
        Node nodeCenter = new BasicNode(0, 0);
        starMap.addNode(nodeCenter, null);

        // Set of unidentified stars (affinities)
        Set<Node> stars = new Set1L<>();
        stars.add(new BasicNode(5, 5)); // Close to center
        stars.add(new BasicNode(8, 8)); // Close to center
        stars.add(new BasicNode(100, 100)); // Far away
        stars.add(new BasicNode(105, 105));

        out.println("Clustering stars into the map");
        starMap.nestCluster(stars);

        out.println("Star Map Structure:");
        out.println(starMap.toString());

        out.println("Remaining unclustered stars:");
        for (Node star : stars) {
            out.println(star.toString());
        }

        // Calculate density of the center
        out.println("Density at center: " + starMap.queryDensity(nodeCenter));

        out.close();
    }
}
