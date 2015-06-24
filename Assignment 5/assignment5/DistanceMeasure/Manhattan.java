package assignment5.DistanceMeasure;

import assignment5.Unit;

public class Manhattan implements DistanceMeasure {
    public Manhattan() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        double summedManhattanDistance = 0;
        for (int i = 0; i < unit1.length(); i++) {
            summedManhattanDistance += Math.abs(unit1.elementAt(i) - unit2.elementAt(i));
        }
        return summedManhattanDistance;
    }
}
