package assignment6;

import assignment6.View.Cartesian;
import assignment6.View.Dendrogram;
import assignment6.View.View;
import ui.Colour;
import ui.DrawUserInterface;
import ui.Event;
import ui.UserInterfaceFactory;

public class ClusterUI {
    public static final String CARTESIAN = "2D Cartesian";
    public static final String DENDROGRAM = "Dendrogram";
    private static final Colour BLACK = new Colour(0, 0, 0);
    private static final int NAVIGATION_HEIGHT = 40;
    private static final int TEXT_HEIGHT = 20;
    private static final int BUTTON_HEIGHT = 40;

    private DrawUserInterface ui;
    private int totalClusterSteps;
    private View graph;
    private String xLabel, yLabel;
    private int unitSize;
    private int canvasWidth, canvasHeight, graphPadding;
    private int uiWidth, uiHeight;

    ClusterUI(int graphWidth, int graphHeight, int graphPadding) {
        uiWidth = graphWidth;
        uiHeight = graphHeight + NAVIGATION_HEIGHT;
        ui = UserInterfaceFactory.getDrawUI(uiWidth, uiHeight);
        canvasWidth = graphWidth - 2 * graphPadding;
        canvasHeight = graphHeight - 2 * graphPadding;
        this.graphPadding = graphPadding;
    }

    public String askForChoice(String question, String... options){
        ui.clear();
        drawMenu(question,options);
        ui.showChanges();
        return getResponseFromChoices(options);
    }

    private void drawMenu(String question, String... options) {
        int height = 250;
        int padding = uiWidth / (options.length + 2);
        ui.drawText(padding, height + BUTTON_HEIGHT + TEXT_HEIGHT / 2, question, BLACK);
        for (int i = 0; i < options.length; i++) {
            ui.drawSquare(padding + i * padding, height + BUTTON_HEIGHT, padding, BUTTON_HEIGHT, BLACK, false);
            ui.setSquareHotspot(padding + i * padding, height + BUTTON_HEIGHT, padding, BUTTON_HEIGHT, options[i]);
            ui.drawText(padding + i * padding + padding / 3, height + 15, options[i], BLACK);
        }
    }

    private String getResponseFromChoices(String... answers) {
        while (true) {
            Event e = ui.getEvent();
            if (e.name.equals("click")) {
                for (String answer : answers) {
                    if (e.data.equals(answer)) {
                        return answer;
                    }
                }
            }
        }
    }

    public ClusterUI useView(String view) {
        switch (view) {
            case CARTESIAN:
                graph = new Cartesian(ui, canvasWidth, canvasHeight, graphPadding, xLabel, yLabel);
            case DENDROGRAM:
            default:
                graph = new Dendrogram(ui, canvasWidth, canvasHeight, graphPadding, unitSize);
        }
        return this;
    }

    public ClusterUI toggleView() {
        if (graph instanceof Cartesian) {
            useView(DENDROGRAM);
        } else {
            useView(CARTESIAN);
        }
        return this;
    }

    public void setParameters(Dataset dataset, ClusterRow clusters, String distanceMeasure, String clusterMethod) {
        totalClusterSteps = clusters.size() - dataset.getClusterLimit();
        xLabel = dataset.variableNameAt(0);
        yLabel = dataset.variableNameAt(1);
        unitSize = clusters.size();

    }

    public void render(ClusterRow clusters) {
        ui.clear();
        drawTopBar(unitSize - clusters.size());
        graph.draw(clusters);
    }

    public Event getEvent() {
        return ui.getEvent();
    }

    private void drawTopBar(int clusteringStep) {
        final int openFileLine = 100;
        ui.drawLine(0, uiHeight - NAVIGATION_HEIGHT, uiWidth, uiHeight - NAVIGATION_HEIGHT, BLACK);
        drawStatusText(20, "Open file");
        ui.drawLine(openFileLine, uiHeight - NAVIGATION_HEIGHT, openFileLine, uiHeight, BLACK);
        ui.setSquareHotspot(0, uiHeight, openFileLine, NAVIGATION_HEIGHT, "openFile");
        drawStatusText(openFileLine + 20, "Euclidean");
        ui.drawLine(250, uiHeight - NAVIGATION_HEIGHT, 250, uiHeight, BLACK);
        drawStatusText(270, "Single Linkage");
        drawStatusText(uiWidth - 200, "Clustering step " + clusteringStep + " of " + totalClusterSteps);
    }

    private void drawStatusText(int width, String text) {
        ui.drawText(width, uiHeight - NAVIGATION_HEIGHT / 2 - 5, text, BLACK);
    }

}
