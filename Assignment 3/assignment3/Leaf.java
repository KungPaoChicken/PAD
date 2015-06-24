package assignment3;

public class Leaf implements Cluster {
    private UnitRow leaf;

    Leaf(Unit unit){
        leaf=new UnitRow(1);
        leaf.addUnit(unit);
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
        return leaf;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }
}
