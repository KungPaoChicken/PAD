package assignment2;

import java.util.Scanner;

public class Unit {
    private String name;
    private NumberRow variables;

    Unit(int size, String line) {
        variables = new NumberRow(size);
        Scanner in = new Scanner(line);
        name = in.next();
        for (int i = 0; i < size; i++) {
            variables.add(in.nextDouble());
        }
    }

    Unit(String name, NumberRow variables) {
        this.name = name;
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public double elementAt(int index) {
        return variables.numberAt(index);
    }
}
