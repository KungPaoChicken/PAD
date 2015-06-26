package assignment6.DistanceMeasure;

import assignment6.Unit;

public class Manhattan implements DistanceMeasure {
    private static final String NAME = "Manhattan";

    public Manhattan() {

    }

    @Override
    public double calculateDistance(Unit unit1, Unit unit2) {
        double sumOfManhattanDistances = 0;
        for (int i = 0; i < unit1.size(); i++) {
            sumOfManhattanDistances += Math.abs(unit1.elementAt(i) - unit2.elementAt(i));
        }
        return sumOfManhattanDistances;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
