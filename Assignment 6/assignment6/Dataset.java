package assignment6;

public class Dataset {
    private int clusterLimit, elementsSize, variableSize;
    private String[] variableNames;
    private UnitRow units;

    Dataset(int clusterLimit, int elementsSize, int variableSize, String[] variableNames, UnitRow units) {
        this.clusterLimit = clusterLimit;
        this.elementsSize = elementsSize;
        this.variableSize = variableSize;
        this.variableNames = variableNames;
        this.units = units;
    }

    public int getClusterLimit(){
        return clusterLimit;
    }

    public int getElementsSize(){
        return elementsSize;
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

    //Suggest a way to compare results with the Manual, unsorted results are incredibly difficult to check
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
        String[] names = new String[limit];
        for (int i = 0; i < limit; i++) {
            names[i] = variableNameAt(preselectedIndexes[i]);
        }

        UnitRow preselectedRows = new UnitRow(limit);
        for (int i = 0; i < elementsSize; i++) {
            NumberRow variables = new NumberRow(limit);
            for (int j : preselectedIndexes) {
                variables.addNumber(units.elementAt(i, j));
            }
            preselectedRows.addUnit(new Unit(unitNameAt(i), variables));
        }

        return new Dataset(clusterLimit, elementsSize, limit, names, preselectedRows);
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

    private double meanOfColumn(int index) {
        double sum = 0;
        for (int i = 0; i < elementsSize; i++) {
            sum += units.elementAt(i, index);
        }
        return sum / elementsSize;
    }

    private double standardDeviationOfColumn(int index) {
        double mean = meanOfColumn(index);
        double totalSquaredDifference = 0;
        for (int i = 0; i < elementsSize; i++) {
            totalSquaredDifference += Math.pow(units.elementAt(i, index) - mean, 2);
        }
        return Math.sqrt(totalSquaredDifference / (elementsSize - 1));
    }
}
