package assignment5.Cluster;

import assignment5.Unit;
import assignment5.UnitRow;

public class Leaf implements Cluster {
    private Unit leaf;

    public Leaf(Unit unit){
        leaf=unit;
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public UnitRow getUnits() {
        return new UnitRow(1).addUnit(leaf);
    }

    @Override
    public boolean hasChildren() {
        return false;
    }
}
