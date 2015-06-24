package assignment5.Cluster;

import assignment5.ClusterRow;
import assignment5.UnitRow;

public class Node implements Cluster {
    private ClusterRow children;

    public Node(Cluster child1, Cluster child2) {
        Cluster[] clusters = {child1, child2};
        children = new ClusterRow(clusters);
    }

    @Override
    //Maximum of the edge counts of the path between this node and an underlying leaf, aka. height in trees
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
    //Returns all units contained in this cluster, aka. flatten
    public UnitRow getUnits() {
        //NEED EXACT NUMBER OF NODES OR CLUSTERING WILL HAVE PROBLEMS, GO RECURSIVE
        UnitRow units = new UnitRow(getWidth());
        for(int i=0;i<children.size();i++){
            units.addUnits(children.clusterAt(i).getUnits());
        }
        return units;
    }

    @Override
    public boolean hasChildren() {
        return true;
    }
}
