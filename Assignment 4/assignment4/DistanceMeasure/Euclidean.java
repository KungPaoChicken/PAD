package assignment4.DistanceMeasure;

import assignment4.Unit;

public class Euclidean implements DistanceMeasure {
    public Euclidean() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        double summedSquaredDifference = 0;
        for (int i = 0; i < unit1.length(); i++) {
            summedSquaredDifference += Math.pow(unit1.elementAt(i) - unit2.elementAt(i), 2);
        }
        return Math.sqrt(summedSquaredDifference);
    }
}
