package components.nebulanest;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Basic implementation of NebulaNestKernel.Node for testing purposes.
 */
public class BasicNode implements NebulaNestKernel.Node {

    private int x;
    private int y;
    private Queue<NebulaNestKernel.Node> children;

    public BasicNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.children = new Queue1L<>();
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public Queue<NebulaNestKernel.Node> getChildren() {
        return this.children;
    }

    @Override
    public NaturalNumber distanceToSquared(NebulaNestKernel.Node other) {
        long dx = this.x - other.getX();
        long dy = this.y - other.getY();
        long distSquared = dx * dx + dy * dy;

        int result = (int) distSquared;
        if (distSquared > Integer.MAX_VALUE) {
            result = Integer.MAX_VALUE;
        }

        return new NaturalNumber2(result);

    }

    @Override
    public String toString() {
        return "Node(" + this.x + ", " + this.y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof NebulaNestKernel.Node)) {
            return false;
        }
        NebulaNestKernel.Node other = (NebulaNestKernel.Node) obj;
        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return this.x + this.y;
    }
}
