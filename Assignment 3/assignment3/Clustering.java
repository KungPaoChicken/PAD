package assignment3;

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
            ClusterRow clusters=new ClusterRow(p);
            System.out.print("Maximum value of first cluster: ");
            System.out.printf("%.6f",clusters.clusterAt(0).getUnits().maxElement());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
