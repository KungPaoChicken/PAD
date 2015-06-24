package assignment4;

public class NumberRow {
    private double[] numbers;
    private int length;

    NumberRow(int size) {
        numbers = new double[size];
        length = 0;
    }

    public int length(){
        return length;
    }

    public NumberRow addNumber(double number) {
        numbers[length] = number;
        length++;
        return this;
    }

    public NumberRow changeNumberTo(int index, double newNumber) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        numbers[index]=newNumber;
        return this;
    }

    public double numberAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return numbers[index];
    }

    public int maxIndex() {
        double maxValue = numbers[0];
        int maxIndex = 0;
        for (int i = 1; i < length; i++) {
            if (numbers[i] > maxValue) {
                maxValue = numbers[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
