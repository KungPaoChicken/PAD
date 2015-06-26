package assignment6;

public class Unit {
    private String name;
    private NumberRow variables;

    Unit(String name, NumberRow variables) {
        this.name = name;
        this.variables = variables;
    }

    public int size() {
        return variables.size();
    }

    public String getName() {
        return name;
    }

    public double elementAt(int index) {
        return variables.numberAt(index);
    }

    public double calculateMean() {
        double sum = 0;
        for (int i = 0; i < size(); i++) {
            sum += elementAt(i);
        }
        return sum / size();
    }

    public Unit copy() {
        return new Unit(name, variables.copy());
    }
}
