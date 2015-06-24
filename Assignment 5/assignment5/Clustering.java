package assignment5;

import assignment5.ClusterMethod.CompleteLinkage;
import assignment5.DistanceMeasure.Euclidean;
import assignment5.View.Cartesian;
import assignment5.View.View;
import ui.DrawUserInterface;
import ui.Event;
import ui.UserInterfaceFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

public class Clustering {
    private static final String FILENAME = "flowers_petal.txt";
    private static final int PRESELECTION_LIMIT = 50;

    private static final int GRAPH_WIDTH = 800;
    private static final int GRAPH_HEIGHT = 450;
    private static final int GRAPH_PADDING = 50;
    private static final int UI_WIDTH = GRAPH_WIDTH + 2 * GRAPH_PADDING;
    private static final int UI_HEIGHT = GRAPH_HEIGHT + 2 * GRAPH_PADDING;

    private DrawUserInterface ui;

    Clustering() {
        Locale.setDefault(Locale.US);
        ui = UserInterfaceFactory.getDrawUI(UI_WIDTH, UI_HEIGHT);
    }

    void start() throws FileNotFoundException {
        File file = new File(FILENAME);
        //Doesn't look very semantic, really hard to refactor
        Dataset dataset = Parser.fromFile(file).normalize().preselect(PRESELECTION_LIMIT);
        ClusterRow clusters = new ClusterRow(dataset);
        Clusterer clusterer = new Clusterer(clusters, new CompleteLinkage(new Euclidean()), dataset.clusterLimit);

        View layout = new Cartesian(ui, GRAPH_WIDTH, GRAPH_HEIGHT, GRAPH_PADDING, dataset.variableNameAt(0), dataset.variableNameAt(1));
        layout.draw(clusters);

        while (true) {
            Event e = ui.getEvent();
            if (e.name.equals("other_key") && e.data.equals("Space")) {
                clusterer.cluster();
                layout.draw(clusters);
            }
        }
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
