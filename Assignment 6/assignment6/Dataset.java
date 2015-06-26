package assignment6;

public class Dataset {
    private int clusterLimit, elementsSize, variableSize;
    private String elementType;
    private String[] variableNames;
    private UnitRow units;

    Dataset(int clusterLimit, int elementsSize, int variableSize, String elementType, String[] variableNames, UnitRow units) {
        this.clusterLimit = clusterLimit;
        this.elementsSize = elementsSize;
        this.variableSize = variableSize;
        this.elementType = elementType;
        this.variableNames = variableNames;
        this.units = units;
    }

    public int getClusterLimit() {
        return clusterLimit;
    }

    public int getElementsSize() {
        return elementsSize;
    }

    public int getVariableSize() {
        return variableSize;
    }

    public String getElementType() {
        return elementType;
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

    //Normalize preserves the dataset object, since its an operation on all of the units
    //Normalization using min-max normalization: (x-min)/(max-min)
    public Dataset normalize() {
        double[] minVariables = new double[variableSize];
        double[] maxMinDifference = new double[variableSize];
        for (int i = 0; i < variableSize; i++) {
            minVariables[i] = minOfColumn(i);
            maxMinDifference[i] = maxOfColumn(i) - minVariables[i];
        }
        UnitRow normalizedUnits = new UnitRow(elementsSize);
        for (int i = 0; i < elementsSize; i++) {
            NumberRow variables = new NumberRow(variableSize);
            for (int j = 0; j < variableSize; j++) {
                variables.addNumber((units.elementAt(i, j) - minVariables[j]) / maxMinDifference[j]);
            }
            normalizedUnits.addUnit(new Unit(unitNameAt(i), variables));
        }

        units = normalizedUnits;
        return this;
    }

    //Preselection generates a whole new object and let the garbage collector have the old one
    //Selecting something on the other hand is sacrificing others
    public Dataset preselect(int limit) {
        if (variableSize <= limit) {
            return this;
        }

        NumberRow deviations = new NumberRow(variableSize);
        for (int i = 0; i < variableSize; i++) {
            deviations.addNumber(standardDeviationOfColumn(i));
        }

        int[] preselectedIndexes = new int[limit];
        for (int i = 0; i < limit; i++) {
            preselectedIndexes[i] = deviations.maxIndex();
            deviations.changeNumberTo(preselectedIndexes[i], -Double.MAX_VALUE);    //Removing entries move indexes, changing it preserves it
        }

        //Constructing the new dataset
        String[] preselectedNames = new String[limit];
        for (int i = 0; i < limit; i++) {
            preselectedNames[i] = variableNameAt(preselectedIndexes[i]);
        }

        UnitRow preselectedRows = new UnitRow(limit);
        for (int i = 0; i < elementsSize; i++) {
            NumberRow variables = new NumberRow(limit);
            for (int j : preselectedIndexes) {
                variables.addNumber(units.elementAt(i, j));
            }
            preselectedRows.addUnit(new Unit(unitNameAt(i), variables));
        }

        return new Dataset(clusterLimit, elementsSize, limit, elementType, preselectedNames, preselectedRows);
    }

    private double minOfColumn(int index) {
        double min = units.elementAt(0, index);
        for (int i = 1; i < elementsSize; i++) {
            min = Math.min(min, units.elementAt(i, index));
        }
        return min;
    }

    private double maxOfColumn(int index) {
        double max = units.elementAt(0, index);
        for (int i = 1; i < elementsSize; i++) {
            max = Math.max(max, units.elementAt(i, index));
        }
        return max;
    }

    private double standardDeviationOfColumn(int index) {
        double mean = meanOfColumn(index);
        double sumOfSquaredDifferences = 0;
        for (int i = 0; i < elementsSize; i++) {
            sumOfSquaredDifferences += Math.pow(units.elementAt(i, index) - mean, 2);
        }
        return Math.sqrt(sumOfSquaredDifferences / (elementsSize - 1));
    }

    private double meanOfColumn(int index) {
        double sum = 0;
        for (int i = 0; i < elementsSize; i++) {
            sum += units.elementAt(i, index);
        }
        return sum / elementsSize;
    }
}
