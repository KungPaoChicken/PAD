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

    private Clusterer clusterer;
    ClusterUI ui;

    Clustering() {
        Locale.setDefault(Locale.US);
        ui = new ClusterUI(GRAPH_WIDTH, GRAPH_HEIGHT, PADDING);
    }

    private String askForDistanceMeasure() {
        return ui.askForChoice("Please select the distance measure", "Manhattan", "Euclidean", "Pearson");
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

    private String askForClusterMethod() {
        return ui.askForChoice("Please select the cluster linkage", "Single", "Average", "Complete");
    }

    private ClusterMethod getClusterMethod(String clusteringMethod, DistanceMeasure distanceMeasure) {
        switch (clusteringMethod) {
            case "Single":
                return new SingleLinkage(distanceMeasure);
            case "Average":
                return new AverageLinkage(distanceMeasure);
            case "Complete":
            default:
                return new CompleteLinkage(distanceMeasure);
        }
    }

    private void showClustersNames(String name) {
        for (String action : ClusterUI.ACTIONS) {
            if (name.equals(action)) {
                return;
            }
        }
        ui.printf(name);
    }

    private void processEvents(ClusterUI ui) {
        while (true) {
            Event e = ui.getEvent();
            if (e.name.equals("mouseover")) {
                showClustersNames(e.data);
            }
            if (e.name.equals("mouseexit")) {
                ui.clearStatusBar();
            }
            if (e.name.equals("click")) {
                switch (e.data) {
                    case ClusterUI.OPEN_FILE:
                        makeViewFromFile();
                        break;
                    case ClusterUI.SET_DISTANCE_MEASURE:
                        clusterer.getClusterMethod().setDistanceMeasure(getDistanceMeasure(askForDistanceMeasure()));
                        clusterer.reset();
                        ui.getClustererInfo(clusterer).render(clusterer.getClusters());
                        break;
                    case ClusterUI.SET_CLUSTER_METHOD:
                        clusterer.setClusterMethod(getClusterMethod(askForClusterMethod(), clusterer.getClusterMethod().getDistanceMeasure())).reset();
                        ui.getClustererInfo(clusterer).render(clusterer.getClusters());
                        break;
                    case ClusterUI.TOGGLE_VIEW:
//                        clusterer.reset();
                        ui.toggleView().render(clusterer.getClusters());
                        break;
                }
            }
            if ((e.name.equals("other_key") && e.data.equals("Space")) || (e.name.equals("arrow") && e.data.equals("R"))) {
                clusterer.cluster();
                ui.render(clusterer.getClusters());
            }
            if (e.name.equals("other_key") && e.data.equals("Escape")) {
                System.exit(0);
                break;
            }
        }
    }

    public void makeViewFromFile() {
        if (!UIAuxiliaryMethods.askUserForInput()) {
            System.out.println("File opening cancelled/failed");
        } else {
            Dataset dataset = Parser.fromScanner(new Scanner(System.in)).normalize().preselect(PRESELECTION_LIMIT);
            ClusterRow clusters = new ClusterRow(dataset);

            String distanceMeasure = askForDistanceMeasure();
            String clusterMethod = askForClusterMethod();
            ClusterMethod cm = getClusterMethod(clusterMethod, getDistanceMeasure(distanceMeasure));
            clusterer = new Clusterer(clusters, cm, dataset.getClusterLimit());

            String view = ui.askForChoice("Please select the view", "Cartesian", "Dendrogram");
            ui.getDatasetInfo(dataset).getClustererInfo(clusterer);
            ui.useView(view);
            ui.render(clusterer.getClusters());
        }
    }

    public void start() throws FileNotFoundException {
        makeViewFromFile();
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
