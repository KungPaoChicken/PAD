package assignment1;

public class UnitRow {
    private Unit[] row;
    private int length;

    UnitRow(int size) {
        row = new Unit[size];
        length = 0;
    }

    public UnitRow addUnit(Unit unit) {
        row[length] = unit;
        length++;
        return this;
    }

    public Unit unitAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return row[index];
    }
}
