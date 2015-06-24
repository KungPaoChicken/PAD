package assignment6.DistanceMeasure;

import assignment6.Unit;

public class Euclidean implements DistanceMeasure {
    private static final String NAME="Euclidean";

    public Euclidean() {

    }

    public double calculateDistance(Unit unit1, Unit unit2) {
        double summedSquaredDifference = 0;
        for (int i = 0; i < unit1.size(); i++) {
            summedSquaredDifference += Math.pow(unit1.elementAt(i) - unit2.elementAt(i), 2);
        }
        return Math.sqrt(summedSquaredDifference);
    }

    @Override
    public String getName(){
        return NAME;
    }
}
