package assignment5;

public class Unit {
    private String name;
    private NumberRow variables;

    Unit(String name, NumberRow variables) {
        this.name = name;
        this.variables = variables;
    }

    public int length(){
        return variables.length();
    }

    public String getName() {
        return name;
    }

    public double elementAt(int index) {
        return variables.numberAt(index);
    }
}
