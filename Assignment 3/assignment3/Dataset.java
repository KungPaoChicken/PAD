package assignment3;

import java.util.Arrays;
import java.util.Scanner;

public class Dataset {
    public int clusterSize, elementsSize, variableSize;
    private String[] variableNames;
    private UnitRow units;

    Dataset(int clusterSize, int elementsSize, int variableSize, String[] variableNames, UnitRow units) {
        this.clusterSize = clusterSize;
        this.elementsSize = elementsSize;
        this.variableSize = variableSize;
        this.variableNames = variableNames;
        this.units = units;
    }

    Dataset(Scanner in) {
        clusterSize = in.nextInt();
        elementsSize = in.nextInt();
        variableSize = in.nextInt();
        in.next();
        variableNames = new String[variableSize];
        for (int i = 0; i < variableSize; i++) {
            variableNames[i] = in.next();
        }

        units = new UnitRow(elementsSize);
        in.nextLine();  //have to skip the header line for some reason, need a read on Scanner

        for (int i = 0; i < elementsSize; i++) {
            if (in.hasNextLine()) {
                units.addUnit(new assignment3.Unit(variableSize, in.nextLine()));
            }
        }

        in.close();
    }

    public int getVariableSize() {
        return variableSize;
    }

    public String variableNameAt(int index) {
        if (index > variableSize) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return variableNames[index];
    }

    public String unitNameAt(int index) {
        return units.nameAt(index);
    }

    public Unit unitAt(int rowIndex) {
        return units.unitAt(rowIndex);
    }

    public double maxOfVariableAt(int index) {
        double max = units.elementAt(0, index);
        for (int i = 1; i < elementsSize; i++) {
            max = Math.max(max, units.elementAt(i, index));
        }
        return max;
    }

    public double minOfVariableAt(int index) {
        double min = units.elementAt(0, index);
        for (int i = 1; i < elementsSize; i++) {
            min = Math.min(min, units.elementAt(i, index));
        }
        return min;
    }

    public Dataset normalize() {
        double[] minVariables = new double[variableSize];
        double[] maxMinDifference = new double[variableSize];
        for (int i = 0; i < variableSize; i++) {
            minVariables[i] = minOfVariableAt(i);
            maxMinDifference[i] = maxOfVariableAt(i) - minVariables[i];
        }
        UnitRow normalizedRows = new UnitRow(elementsSize);
        for (int i = 0; i < elementsSize; i++) {
            assignment3.NumberRow variables = new assignment3.NumberRow(variableSize);
            for (int j = 0; j < variableSize; j++) {
                variables.addNumber((units.elementAt(i, j) - minVariables[j]) / maxMinDifference[j]);
            }
            normalizedRows.addUnit(new assignment3.Unit(unitNameAt(i), variables));
        }
        return new Dataset(clusterSize, elementsSize, variableSize, variableNames, normalizedRows);
    }

    private double calculateStandardDeviation(int variableIndex) {
        double sum = 0;
        for (int i = 0; i < elementsSize; i++) {
            sum += units.elementAt(i, variableIndex);
        }
        double mean = sum / elementsSize;

        double totalSquaredDifference = 0;
        for (int i = 0; i < elementsSize; i++) {
            totalSquaredDifference += Math.pow(units.elementAt(i, variableIndex) - mean, 2);
        }
        return Math.sqrt(totalSquaredDifference / (elementsSize - 1));
    }

    public Dataset preselect(int limit) {
        if (variableSize <= limit) {
            return this;
        }

        assignment3.NumberRow deviations = new assignment3.NumberRow(variableSize);
        for (int i = 0; i < variableSize; i++) {
            deviations.addNumber(calculateStandardDeviation(i));
        }

        int[] preselectedIndexes = new int[limit];
        for (int i = 0; i < limit; i++) {
            preselectedIndexes[i] = deviations.maxIndex();  //Removing entry changes indexes, need max without removing
            deviations.changeNumberTo(preselectedIndexes[i],-Double.MAX_VALUE);       //Consider setting it to -Double.MAX_VALUE
        }

        Arrays.sort(preselectedIndexes);

        UnitRow preselectedRows = new UnitRow(limit);

        String[] names = new String[limit];
        for (int i = 0; i < preselectedIndexes.length; i++) {
            names[i] = variableNameAt(preselectedIndexes[i]);
        }

        for (int i = 0; i < elementsSize; i++) {
            assignment3.NumberRow variables = new assignment3.NumberRow(limit);
            for (int j : preselectedIndexes) {
                variables.addNumber(units.elementAt(i, j));
            }
            preselectedRows.addUnit(new assignment3.Unit(unitNameAt(i), variables));
        }
        return new Dataset(clusterSize, elementsSize, limit, names, preselectedRows);
    }

}
