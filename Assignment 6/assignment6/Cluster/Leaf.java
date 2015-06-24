package assignment6.Cluster;

import assignment6.Unit;
import assignment6.UnitRow;

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

    @Override
    public Leaf copy(){
        return new Leaf(leaf.copy());
    }
}
