package assignment1;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Processing {

    private static final String FILENAME = "flowers_sepalpetal.txt";

    Processing() {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        try {
            Scanner file = new Scanner(new File(FILENAME));
            Dataset d = new Dataset(file);

            int indexOfVariable = 0;
            System.out.printf("The maximum value of the variable `%s' is %.6f", d.variableNameAt(indexOfVariable), d.maxOfVariableAt(indexOfVariable));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
