package components.nebulanest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.nebulanest.NebulaNestKernel.Node;

public class NebulaNest1LTest {

    @Test
    public void testConstructor() {
        NebulaNest nest = new NebulaNest1L();
        assertNotNull(nest);
        assertTrue(nest.isEmpty());
    }

    @Test
    public void testAddNodeAndGetRoot() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);

        assertEquals(root, nest.getRoot());
        assertEquals(false, nest.isEmpty());
    }

    @Test
    public void testAddChildNode() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);

        Node child = new BasicNode(1, 1);
        nest.addNode(child, root);

        assertEquals(1, root.getChildren().length());
        assertEquals(child, root.getChildren().front());
    }

    @Test
    public void testClear() {
        NebulaNest nest = new NebulaNest1L();
        Node root = new BasicNode(0, 0);
        nest.addNode(root, null);
        nest.clear();
        assertTrue(nest.isEmpty());
    }

    @Test
    public void testNewInstance() {
        NebulaNest nest = new NebulaNest1L();
        NebulaNest nest2 = nest.newInstance();
        assertNotNull(nest2);
        assertTrue(nest2.isEmpty());
        // Verify types match (if possible/relevant, or just that it's a NebulaNest)
        assertEquals(nest.getClass(), nest2.getClass());
    }

    @Test
    public void testTransferFrom() {
        NebulaNest nest1 = new NebulaNest1L();
        Node root = new BasicNode(10, 20);
        nest1.addNode(root, null);

        NebulaNest nest2 = new NebulaNest1L();
        nest2.transferFrom(nest1);

        assertTrue(nest1.isEmpty());
        assertEquals(root, nest2.getRoot());
    }

}
