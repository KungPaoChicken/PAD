package assignment5;

public class UnitRow {
    private Unit[] units;
    private int length;

    public UnitRow(int size) {
        units = new Unit[size];
        length = 0;
    }

    public int size(){
        return length;
    }

    public UnitRow addUnit(Unit unit) {
        units[length] = unit;
        length++;
        return this;
    }

    public UnitRow addUnits(UnitRow units){
        for(int i=0;i<units.size();i++){
            addUnit(units.unitAt(i));
        }
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
}
