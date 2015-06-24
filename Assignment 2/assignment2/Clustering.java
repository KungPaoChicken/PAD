package assignment2;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Clustering {
    private static final String FILENAME = "tumors.txt";
    private static final int PRESELECTION_LIMIT = 50;

    Clustering() {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        try {
            Scanner file = new Scanner(new File(FILENAME));
            Dataset d = new Dataset(file);
            Dataset p = d.normalize().preselect(PRESELECTION_LIMIT);    //DO NOT OPTIMIZE, SWITCHING ORDER GIVES DIFFERENT RESULTS
            System.out.println("Variables after preselection:");
            System.out.print(p.variableNameAt(0));
            for (int i = 1; i < p.getVariableSize(); i++) {
                System.out.printf(", %s", p.variableNameAt(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
