package assignment1;

import java.util.Scanner;

public class Dataset {
    private int clusterSize, elementsSize, variableSize;
    private String[] variableNames;
    private UnitRow elements;

    Dataset(Scanner in) {
        clusterSize = in.nextInt();
        elementsSize = in.nextInt();
        variableSize = in.nextInt();
        in.next();
        variableNames = new String[variableSize];
        for (int i = 0; i < variableSize; i++) {
            variableNames[i] = in.next();
        }

        elements = new UnitRow(elementsSize);
        in.nextLine();  //have to skip the header line for some reason, need a read on Scanner

        for (int i = 0; i < elementsSize; i++) {
            if (in.hasNextLine()) {
                elements.addUnit(new Unit(variableSize, in.nextLine()));
            }
        }

        in.close();
    }

    public String variableNameAt(int index) {
        if (index > variableSize) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return variableNames[index];
    }

    public double maxOfVariableAt(int index) {
        double max = elements.unitAt(0).variableAt(index);
        for (int i = 1; i < elementsSize; i++) {
            max = Math.max(max, elements.unitAt(i).variableAt(index));
        }
        return max;
    }
}
