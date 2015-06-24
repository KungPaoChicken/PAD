package assignment6;

import assignment6.View.Cartesian;
import assignment6.View.Dendrogram;
import assignment6.View.View;
import ui.Colour;
import ui.DrawUserInterface;
import ui.Event;
import ui.UserInterfaceFactory;

public class ClusterUI {
    public static final String CARTESIAN = "Cartesian";
    public static final String DENDROGRAM = "Dendrogram";
    private static final Colour BLACK = new Colour(0, 0, 0);
    private static final Colour WHITE = new Colour(255, 255, 255);
    private static final int BAR_HEIGHT = 40;
    private static final int MENU_Y=250;
    private static final int BUTTON_HEIGHT = 40;
    private static final int TEXT_HEIGHT = 20;

    private DrawUserInterface ui;
    private int canvasWidth, canvasHeight, graphPadding;
    private int uiWidth, uiHeight;
    private View graph;

    private String elementType;
    private String clusterMethod, distanceMeasure;
    private String xLabel, yLabel;
    private int totalClusterSteps;
    private int unitSize;

    ClusterUI(int graphWidth, int graphHeight, int graphPadding) {
        uiWidth = graphWidth;
        uiHeight = graphHeight + BAR_HEIGHT;
        ui = UserInterfaceFactory.getDrawUI(uiWidth, uiHeight);
        canvasWidth = graphWidth - 2 * graphPadding;
        canvasHeight = graphHeight - 2 * graphPadding;
        this.graphPadding = graphPadding;
    }

    public String askForChoice(String question, String... options) {
        ui.clear();
        drawMenu(question, options);
        ui.showChanges();
        return getResponseFromChoices(options);
    }

    private void drawMenu(String question, String... options) {
        int padding = uiWidth / (options.length + 2);
        ui.drawText(padding, MENU_Y + BUTTON_HEIGHT + TEXT_HEIGHT / 2, question, BLACK);
        for (int i = 0; i < options.length; i++) {
            ui.drawSquare(padding + i * padding, MENU_Y + BUTTON_HEIGHT, padding, BUTTON_HEIGHT, BLACK, false);
            ui.setSquareHotspot(padding + i * padding, MENU_Y + BUTTON_HEIGHT, padding, BUTTON_HEIGHT, options[i]);
            ui.drawText(padding + i * padding + padding / 3, MENU_Y + 15, options[i], BLACK);
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

    public void extractData(Dataset dataset, Clusterer clusterer) {
        elementType = dataset.getElementType();
        xLabel = dataset.variableNameAt(0);
        yLabel = dataset.variableNameAt(1);
        totalClusterSteps = dataset.getElementsSize() - dataset.getClusterLimit();
        unitSize = dataset.getElementsSize();
        distanceMeasure = clusterer.getClusterMethod().getDistanceMeasure().getName();
        clusterMethod = clusterer.getClusterMethod().getName();
    }

    public void useView(String view) {
        switch (view) {
            case CARTESIAN:
                graph = new Cartesian(ui, canvasWidth, canvasHeight, graphPadding, xLabel, yLabel);
                break;
            case DENDROGRAM:
            default:
                graph = new Dendrogram(ui, canvasWidth, canvasHeight, graphPadding, unitSize);
        }
    }

    public ClusterUI toggleView() {
        if (graph instanceof Cartesian) {
            useView(DENDROGRAM);
        } else {
            useView(CARTESIAN);
        }
        return this;
    }

    public void render(ClusterRow clusters) {
        ui.clear();
        graph.draw(clusters);
        drawTopBar(unitSize - clusters.size());
        ui.showChanges();
    }

    public Event getEvent() {
        return ui.getEvent();
    }

    private void drawTopBar(int clusteringStep) {
        final int textPadding = 20;

        ui.drawSquare(0, uiHeight, uiWidth, BAR_HEIGHT, WHITE, true);
        ui.drawLine(0, uiHeight - BAR_HEIGHT, uiWidth, uiHeight - BAR_HEIGHT, BLACK);

        drawStatusText(textPadding, "Open file");
        ui.setSquareHotspot(0, uiHeight, 100, BAR_HEIGHT, "openFile");

        drawDivider(100);
        drawStatusText(100 + textPadding, elementType);

        drawDivider(250);
        drawStatusText(250 + textPadding, distanceMeasure);

        drawDivider(350);
        drawStatusText(350 + textPadding, clusterMethod);

        drawStatusText(uiWidth - 200, "Clustering step " + clusteringStep + " of " + totalClusterSteps);
    }

    private void drawDivider(int x){
        ui.drawLine(x, uiHeight - BAR_HEIGHT, x, uiHeight, BLACK);
    }

    private void drawStatusText(int width, String text) {
        ui.drawText(width, uiHeight - BAR_HEIGHT / 2 - 5, text, BLACK);
    }

}
