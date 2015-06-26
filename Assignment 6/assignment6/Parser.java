package assignment6;

import java.util.Scanner;

public class Parser {
    private static int elementSize, variableSize;

    public static Dataset fromScanner(Scanner scanner) {
        int clusterLimit = scanner.nextInt();
        elementSize = scanner.nextInt();
        variableSize = scanner.nextInt();

        String elementType = scanner.next();
        String[] variableNames = scanVariableNames(scanner);

        scanner.nextLine();  //Going pass the header line
        UnitRow units = scanUnits(scanner);

        scanner.close();
        return new Dataset(clusterLimit, elementSize, variableSize, elementType, variableNames, units);
    }

    private static String[] scanVariableNames(Scanner scanner) {
        String[] variableNames = new String[variableSize];
        for (int i = 0; i < variableSize; i++) {
            variableNames[i] = scanner.next();
        }
        return variableNames;
    }

    private static UnitRow scanUnits(Scanner scanner) {
        UnitRow units = new UnitRow(elementSize);
        for (int i = 0; i < elementSize; i++) {
            if (scanner.hasNextLine()) {
                units.addUnit(scanUnit(scanner));
            }
        }
        return units;
    }

    private static Unit scanUnit(Scanner scanner) {
        String name = scanner.next();
        NumberRow variables = new NumberRow(variableSize);
        for (int i = 0; i < variableSize; i++) {
            variables.addNumber(scanner.nextDouble());
        }
        return new Unit(name, variables);
    }
}
