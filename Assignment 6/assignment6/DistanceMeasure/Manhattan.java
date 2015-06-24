package assignment6.DistanceMeasure;

import assignment6.Unit;

public class Manhattan implements DistanceMeasure {
    public Manhattan() {

    }

    public double calculateDistance(Unit unit1, Unit unit2) {
        double summedManhattanDistance = 0;
        for (int i = 0; i < unit1.size(); i++) {
            summedManhattanDistance += Math.abs(unit1.elementAt(i) - unit2.elementAt(i));
        }
        return summedManhattanDistance;
    }
}
