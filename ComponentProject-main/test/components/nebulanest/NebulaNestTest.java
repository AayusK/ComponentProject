package components.nebulanest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.nebulanest.NebulaNestKernel.Node;
import components.set.Set;
import components.set.Set1L;

public class NebulaNestTest {

    @Test
    public void testToStringEmpty() {
        NebulaNest nest = new NebulaNest1L();
        assertEquals("NebulaNest[]", nest.toString());
    }

    @Test
    public void testEqualsEmpty() {
        NebulaNest nest1 = new NebulaNest1L();
        NebulaNest nest2 = new NebulaNest1L();
        assertTrue(nest1.equals(nest2));
        assertTrue(nest2.equals(nest1));
    }

    @Test
    public void testGetSize() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);
        Node child1 = new BasicNode(10, 10);
        nest.addNode(child1, root);

        assertEquals(new NaturalNumber2(2), nest.getSize());
    }

    @Test
    public void testQueryDensity() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);

        Node closeNode = new BasicNode(3, 4);
        nest.addNode(closeNode, root);

        Node farNode = new BasicNode(20, 20);
        nest.addNode(farNode, root);

        NaturalNumber density = nest.queryDensity(root);
        assertEquals(new NaturalNumber2(2), density);
    }

    @Test
    public void testNestCluster() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);

        Set<Node> affinities = new Set1L<>();
        Node n1 = new BasicNode(3, 4); // Close
        Node n2 = new BasicNode(50, 50); // Far

        affinities.add(n1);
        affinities.add(n2);

        nest.nestCluster(affinities);

        assertFalse(affinities.contains(n1));
        assertTrue(affinities.contains(n2));

        boolean found = false;
        long len = root.getChildren().length();
        for (int i = 0; i < len; i++) {
            Node c = root.getChildren().dequeue();
            if (c.equals(n1))
                found = true;
            root.getChildren().enqueue(c);
        }
        assertTrue(found);
    }

    @Test
    public void testRemoveNode() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);
        Node child = new BasicNode(10, 10);
        nest.addNode(child, root);

        // Before removal
        assertEquals(new NaturalNumber2(2), nest.getSize());

        nest.removeNode(child);

        // After removal
        assertEquals(new NaturalNumber2(1), nest.getSize());
        assertEquals(0, root.getChildren().length());
    }

    @Test
    public void testRemoveRoot() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);

        nest.removeNode(root);

        assertTrue(nest.isEmpty());
    }

}
