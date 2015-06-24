package assignment3;

public class UnitRow {
    private Unit[] units;
    private int length;

    UnitRow(int size) {
        units = new Unit[size];
        length = 0;
    }

    public UnitRow addUnit(Unit unit) {
        units[length] = unit;
        length++;
        return this;
    }

    public Unit unitAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return units[index];
    }

    public String nameAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return units[index].getName();
    }

    public double elementAt(int rowIndex, int variableIndex) {
        if (rowIndex >= length) {
            throw new ArrayIndexOutOfBoundsException(rowIndex);
        }
        return units[rowIndex].elementAt(variableIndex);
    }

    public double maxElement(){
        double max=-Double.MAX_VALUE;
        for(Unit unit:units){
            for(int i=0;i<unit.length();i++){
                max=Math.max(max,unit.elementAt(i));
            }
        }
        return max;
    }
}
