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

    public static final String OPEN_FILE = "openFile";
    public static final String SET_DISTANCE_MEASURE = "setDistanceMeasure";
    public static final String SET_CLUSTER_METHOD = "setClusterMethod";
    public static final String TOGGLE_VIEW = "toggleView";
    public static final String[] ACTIONS = new String[]{OPEN_FILE, SET_DISTANCE_MEASURE, SET_CLUSTER_METHOD, TOGGLE_VIEW};

    private static final Colour BLACK = new Colour(0, 0, 0);
    private static final Colour WHITE = new Colour(255, 255, 255);

    private static final int MENU_HEIGHT = 40;
    private static final int MENU_ITEM_WIDTH = 100;
    private static final int TEXT_PADDING = 10;

    private static final int MENU_Y = 250;
    private static final int BUTTON_HEIGHT = 40;
    private static final int TEXT_HEIGHT = 20;

    private DrawUserInterface ui;
    private int canvasWidth, canvasHeight, graphPadding;
    private int uiWidth, uiHeight;
    private View graph;

    private int menuItemX;

    private String xLabel, yLabel;
    private String elementType;
    private String clusterMethod, distanceMeasure;
    private int totalClusterSteps;
    private int unitSize;

    ClusterUI(int graphWidth, int graphHeight, int graphPadding) {
        uiWidth = graphWidth;
        uiHeight = graphHeight + MENU_HEIGHT;
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

    public ClusterUI getDatasetInfo(Dataset dataset) {
        elementType = dataset.getElementType();
        xLabel = dataset.variableNameAt(0);
        yLabel = dataset.variableNameAt(1);
        totalClusterSteps = dataset.getElementsSize() - dataset.getClusterLimit();
        unitSize = dataset.getElementsSize();
        return this;
    }

    public ClusterUI getClustererInfo(Clusterer clusterer) {
        distanceMeasure = clusterer.getClusterMethod().getDistanceMeasure().getName();
        clusterMethod = clusterer.getClusterMethod().getName();
        return this;
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

    public ClusterUI clearStatusBar() {
        ui.clearStatusBar();
        return this;
    }

    public ClusterUI printf(String string, Object... objects) {
        ui.printf(string, objects);
        return this;
    }

    private void drawTopBar(int clusteringStep) {
        menuItemX = 0;    //Reset menu X position every rendering
        ui.drawSquare(0, uiHeight, uiWidth, MENU_HEIGHT, WHITE, true);
        ui.drawSquare(0, uiHeight, uiWidth, MENU_HEIGHT, BLACK, false);
        ui.drawLine(0, uiHeight - MENU_HEIGHT, uiWidth, uiHeight - MENU_HEIGHT, BLACK);

        drawMenuItem("Open file", MENU_ITEM_WIDTH, OPEN_FILE);
        drawMenuItem(elementType, MENU_ITEM_WIDTH, "");
        drawMenuItem(distanceMeasure, MENU_ITEM_WIDTH, SET_DISTANCE_MEASURE);
        drawMenuItem(clusterMethod, 3 * MENU_ITEM_WIDTH / 2, SET_CLUSTER_METHOD);
        drawMenuItem(graph.getName(), MENU_ITEM_WIDTH, TOGGLE_VIEW);

        drawStatusText(uiWidth - 200, "Clustering step " + clusteringStep + " of " + totalClusterSteps);
    }

    private void drawMenuItem(String name, int width, String data) {
        ui.drawSquare(menuItemX, uiHeight, width, MENU_HEIGHT, BLACK, false);
        drawStatusText(menuItemX + TEXT_PADDING, name);
        ui.setSquareHotspot(menuItemX, uiHeight, width, MENU_HEIGHT, data);
        menuItemX += width;
    }

    private void drawStatusText(int width, String text) {
        ui.drawText(width, uiHeight - MENU_HEIGHT / 2 - 5, text, BLACK);
    }

}
