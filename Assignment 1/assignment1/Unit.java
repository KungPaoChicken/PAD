package assignment1;

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

    public double variableAt(int index) {
        return variables.numberAt(index);
    }
}
