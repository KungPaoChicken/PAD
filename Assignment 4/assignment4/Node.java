package assignment4;

public class Node implements Cluster {
    private ClusterRow children;

    Node(Cluster child1, Cluster child2) {
        Cluster[] clusters = {child1, child2};
        children = new ClusterRow(clusters);
    }

    @Override
    //Maximum of the edge counts of the path between this node and an underlying leaf
    public int getDepth() {
        int depth = 0;
        for (int i = 0; i < children.size(); i++) {
            depth = Math.max(depth, children.clusterAt(i).getDepth() + 1);
        }
        return depth;
    }

    @Override
    //Total amount of leaf nodes under a node
    public int getWidth() {
        int width = 0;
        for (int i = 0; i < children.size(); i++) {
            width += children.clusterAt(i).getWidth();
        }
        return width;
    }

    @Override
    public UnitRow getUnits() {
        UnitRow units = new UnitRow(getDepth());
        return units;
    }

    @Override
    public boolean hasChildren() {
        return true;
    }
}
