package assignment6;

public class UnitRow {
    private Unit[] units;
    private int length;

    public UnitRow(int size) {
        units = new Unit[size];
        length = 0;
    }

    public int size() {
        return length;
    }

    public UnitRow addUnit(Unit unit) {
        units[length] = unit;
        length++;
        return this;
    }

    public UnitRow addUnits(UnitRow units) {
        for (int i = 0; i < units.size(); i++) {
            addUnit(units.unitAt(i));
        }
        return this;
    }

    public Unit unitAt(int index) {
        if (index >= size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return units[index];
    }

    public String nameAt(int index) {
        if (index >= size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return unitAt(index).getName();
    }

    public double elementAt(int rowIndex, int variableIndex) {
        if (rowIndex >= size()) {
            throw new ArrayIndexOutOfBoundsException(rowIndex);
        }
        return unitAt(rowIndex).elementAt(variableIndex);
    }

    public UnitRow copy() {
        UnitRow copy = new UnitRow(size());
        for (int i = 0; i < size(); i++) {
            copy.addUnit(unitAt(i).copy());
        }
        return copy;
    }
}
