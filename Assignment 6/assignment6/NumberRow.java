package assignment6;

public class NumberRow {
    private double[] numbers;
    private int length;

    NumberRow(int size) {
        numbers = new double[size];
        length = 0;
    }

    public int size() {
        return length;
    }

    public NumberRow addNumber(double number) {
        numbers[length] = number;
        length++;
        return this;
    }

    public NumberRow changeNumberTo(int index, double newNumber) {
        if (index >= size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        numbers[index] = newNumber;
        return this;
    }

    public double numberAt(int index) {
        if (index >= size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return numbers[index];
    }

    public int maxIndex() {
        double maxValue = numbers[0];
        int maxIndex = 0;
        for (int i = 1; i < size(); i++) {
            if (numbers[i] > maxValue) {
                maxValue = numbers[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public NumberRow copy() {
        NumberRow copy = new NumberRow(length);
        for (double n : numbers) {
            copy.addNumber(n);
        }
        return copy;
    }
}
