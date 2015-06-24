package assignment4;

import assignment4.ClusterMethod.AverageLinkage;
import assignment4.ClusterMethod.CompleteLinkage;
import assignment4.ClusterMethod.SingleLinkage;
import assignment4.DistanceMeasure.Euclidean;
import assignment4.DistanceMeasure.Manhattan;
import assignment4.DistanceMeasure.Pearson;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class Clustering {
    private static final String FILENAME = "milk.txt";
    private static final int PRESELECTION_LIMIT = 50;

    Clustering() {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        try {
            Scanner file = new Scanner(new File(FILENAME));
            Dataset d = new Dataset(file);
            Dataset p = d.normalize().preselect(PRESELECTION_LIMIT);    //DO NOT OPTIMIZE, SWITCHING ORDER GIVES DIFFERENT RESULTS
            ClusterRow clusters = new ClusterRow(p);
            Cluster cluster1 = clusters.clusterAt(0);
            Cluster cluster2 = clusters.clusterAt(1);
            System.out.printf("Euclidean + SingleLinkage:\t\t%.6f\n", new SingleLinkage(new Euclidean()).calculateDistance(cluster1, cluster2));
            System.out.printf("Euclidean + AverageLinkage:\t\t%.6f\n", new AverageLinkage(new Euclidean()).calculateDistance(cluster1,cluster2));
            System.out.printf("Euclidean + CompleteLinkage:\t%.6f\n", new CompleteLinkage(new Euclidean()).calculateDistance(cluster1, cluster2));
            System.out.printf("Manhattan + SingleLinkage:\t\t%.6f\n", new SingleLinkage(new Manhattan()).calculateDistance(cluster1, cluster2));
            System.out.printf("Manhattan + AverageLinkage:\t\t%.6f\n", new AverageLinkage(new Manhattan()).calculateDistance(cluster1, cluster2));
            System.out.printf("Manhattan + CompleteLinkage:\t%.6f\n", new CompleteLinkage(new Manhattan()).calculateDistance(cluster1, cluster2));
            System.out.printf("Pearson + SingleLinkage:\t\t\t%.6f\n", new SingleLinkage(new Pearson()).calculateDistance(cluster1, cluster2));
            System.out.printf("Pearson + AverageLinkage:\t\t%.6f\n", new AverageLinkage(new Pearson()).calculateDistance(cluster1, cluster2));
            System.out.printf("Pearson + CompleteLinkage:\t\t%.6f\n", new CompleteLinkage(new Pearson()).calculateDistance(cluster1, cluster2));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
