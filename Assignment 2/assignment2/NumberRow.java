package assignment2;

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

    public NumberRow changeNumberTo(int index, double newNumber) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        row[index]=newNumber;
        return this;
    }

    public double numberAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return row[index];
    }

    public int maxIndex() {
        double maxValue = row[0];
        int maxIndex = 0;
        for (int i = 1; i < length; i++) {
            if (row[i] > maxValue) {
                maxValue = row[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
