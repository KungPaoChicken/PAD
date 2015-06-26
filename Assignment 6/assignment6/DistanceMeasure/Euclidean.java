package assignment6.DistanceMeasure;

import assignment6.Unit;

public class Euclidean implements DistanceMeasure {
    private static final String NAME = "Euclidean";

    public Euclidean() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        double sumOfSquaredDifferences = 0;
        for (int i = 0; i < unit1.size(); i++) {
            sumOfSquaredDifferences += Math.pow(unit1.elementAt(i) - unit2.elementAt(i), 2);
        }
        return Math.sqrt(sumOfSquaredDifferences);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
