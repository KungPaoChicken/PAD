package assignment6.Cluster;

import assignment6.UnitRow;

public class Node implements Cluster {
    private Cluster child1, child2;

    public Node(Cluster child1, Cluster child2) {
        this.child1 = child1;
        this.child2 = child2;
    }

    public Cluster getChild1() {
        return child1;
    }

    public Cluster getChild2() {
        return child2;
    }

    @Override
    //Maximum of the edge counts of the path between this node and an underlying leaf, aka. height in trees
    public int getDepth() {
        return Math.max(child1.getDepth(), child2.getDepth()) + 1;
    }

    @Override
    //Total amount of leaf nodes under a node
    public int getWidth() {
        return child1.getWidth() + child2.getWidth();
    }

    @Override
    //Returns all leafs contained in this node
    public UnitRow getUnits() {
        return new UnitRow(getWidth()).addUnits(getChild1().getUnits()).addUnits(getChild2().getUnits());
    }

    @Override
    public boolean hasChildren() {
        return true;
    }

    @Override
    public Node copy() {
        return new Node(child1.copy(), child2.copy());
    }
}
