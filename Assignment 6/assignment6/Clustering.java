package assignment6;

import assignment6.ClusterMethod.AverageLinkage;
import assignment6.ClusterMethod.ClusterMethod;
import assignment6.ClusterMethod.CompleteLinkage;
import assignment6.ClusterMethod.SingleLinkage;
import assignment6.DistanceMeasure.DistanceMeasure;
import assignment6.DistanceMeasure.Euclidean;
import assignment6.DistanceMeasure.Manhattan;
import assignment6.DistanceMeasure.Pearson;
import ui.Event;
import ui.UIAuxiliaryMethods;

import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class Clustering {
    private static final int PRESELECTION_LIMIT = 50;

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 450;
    private static final int PADDING = 50;
    private static final int GRAPH_WIDTH = CANVAS_WIDTH + 2 * PADDING;
    private static final int GRAPH_HEIGHT = CANVAS_HEIGHT + 2 * PADDING;

    private ClusterRow clusters;
    private Clusterer clusterer;
    ClusterUI ui;

    Clustering() {
        Locale.setDefault(Locale.US);
    }

    private DistanceMeasure getDistanceMeasure(String distanceMeasure) {
        switch (distanceMeasure) {
            case "Manhattan":
                return new Manhattan();
            case "Pearson":
                return new Pearson();
            case "Euclidean":
            default:
                return new Euclidean();
        }
    }

    private ClusterMethod getClusteringMethod(String clusteringMethod, DistanceMeasure distanceMeasure) {
        switch (clusteringMethod) {
            case "Single linkage":
                return new SingleLinkage(distanceMeasure);
            case "Average linkage":
                return new AverageLinkage(distanceMeasure);
            case "Complete linkage":
            default:
                return new CompleteLinkage(distanceMeasure);
        }
    }

    private void processEvents(ClusterUI ui) {
        while (true) {
            Event e = ui.getEvent();
            if (e.name.equals("click") && e.data.equals("openFile")) {

            }
            if ((e.name.equals("other_key") && e.data.equals("Space")) || (e.name.equals("arrow") && e.data.equals("R"))) {
                clusterer.cluster();
                ui.render(clusters);
            }
            if (e.name.equals("arrow") && e.data.equals("L")) {
                //Going back
                ui.render(clusters);
            }
            if (e.name.equals("letter") && e.data.equals("q")) {
                //quit
                break;
            }
        }
    }

    public void start() throws FileNotFoundException {
        if (!UIAuxiliaryMethods.askUserForInput()) {
            System.out.println("File opening cancelled/failed");
            return;
        }
        Dataset dataset = Parser.fromScanner(new Scanner(System.in)).normalize().preselect(PRESELECTION_LIMIT);
        clusters = new ClusterRow(dataset);

        ui = new ClusterUI(GRAPH_WIDTH, GRAPH_HEIGHT, PADDING);

        String distanceMeasure = ui.askForChoice("Please select the distance measure", "Manhattan", "Euclidean", "Pearson");
        String clusterMethod = ui.askForChoice("Please select the clustering method", "Single", "Average", "Complete");
        ClusterMethod cm = getClusteringMethod(clusterMethod, getDistanceMeasure(distanceMeasure));
        clusterer = new Clusterer(clusters, cm, dataset.getClusterLimit());

        String view = ui.askForChoice("Please select the view", "Cartesian", "Dendrogram");
        ui.extractData(dataset, clusterer);
        ui.useView(view);

        ui.render(clusters);
        processEvents(ui);
    }

    public static void main(String[] args) {
        try {
            new Clustering().start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
