package assignment6.DistanceMeasure;

import assignment6.Unit;

public class Pearson implements DistanceMeasure {
    private static final String NAME = "Pearson";

    public Pearson() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        return 1 - pearsonCorrelationOf(unit1, unit2);
    }

    private double pearsonCorrelationOf(Unit unit1, Unit unit2) {
        double meanUnit1 = unit1.calculateMean();
        double meanUnit2 = unit2.calculateMean();
        double stdDevUnit1 = standardDeviationOf(unit1);
        double stdDevUnit2 = standardDeviationOf(unit2);
        double productsOfStandardScores = 0;
        for (int i = 0; i < unit1.size(); i++) {
            productsOfStandardScores += ((unit1.elementAt(i) - meanUnit1) / stdDevUnit1) * ((unit2.elementAt(i) - meanUnit2) / stdDevUnit2);
        }
        return productsOfStandardScores / (unit1.size() - 1);
    }

    private double standardDeviationOf(Unit unit) {
        double mean = unit.calculateMean();
        double totalSquaredDifference = 0;
        for (int i = 0; i < unit.size(); i++) {
            totalSquaredDifference += Math.pow(unit.elementAt(i) - mean, 2);
        }
        return Math.sqrt(totalSquaredDifference / (unit.size() - 1));
    }


    @Override
    public String getName() {
        return NAME;
    }
}
