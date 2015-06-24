package assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    static int clusterLimit, elementSize, variableSize;

    public static Dataset fromFile(File file) throws FileNotFoundException {
        Scanner in = new Scanner(file);
        clusterLimit = in.nextInt();
        elementSize = in.nextInt();
        variableSize = in.nextInt();
        in.next();
        String[] variableNames = new String[variableSize];
        for (int i = 0; i < variableSize; i++) {
            variableNames[i] = in.next();
        }

        in.nextLine();  //have to skip the header line for some reason, need a read on Scanner
        UnitRow units = new UnitRow(elementSize);
        for (int i = 0; i < elementSize; i++) {
            if (in.hasNextLine()) {
                units.addUnit(scanUnit(in));
            }
        }

        in.close();
        return new Dataset(clusterLimit, elementSize, variableSize, variableNames, units);
    }

    private static Unit scanUnit(Scanner line) {
        String name = line.next();
        NumberRow variables = new NumberRow(variableSize);
        for (int i = 0; i < variableSize; i++) {
            variables.addNumber(line.nextDouble());
        }
        return new Unit(name, variables);
    }
}
