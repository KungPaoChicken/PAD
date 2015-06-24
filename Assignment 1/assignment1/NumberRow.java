package assignment1;

public class NumberRow {
    private double[] row;
    private int length;

    NumberRow(int size) {
        row = new double[size];
        length = 0;
    }

    public NumberRow add(double number) {
        row[length] = number;
        length++;
        return this;
    }

    public double numberAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return row[index];
    }
}
