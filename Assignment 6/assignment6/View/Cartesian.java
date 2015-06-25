package assignment6.View;

import assignment6.Cluster.Cluster;
import assignment6.ClusterRow;
import assignment6.UnitRow;
import ui.Colour;
import ui.DrawUserInterface;

public class Cartesian implements View {
    private static final String NAME="Cartesian";
    private static final Colour BLACK = new Colour(0, 0, 0);
    //getTextHeight gives a constant anyway
    private static final int TEXT_HEIGHT = 21;
    //Offset value for the y-axis label
    //Because getTextWidth()'s implementation returns the same value for EVERY PIECE OF STRING
    private static final int YLABEL_OFFSET = 30;
    private static final int POINT_SIZE = 10;

    private DrawUserInterface canvas;
    private int width, height, padding;
    private String xLabel, yLabel;

    public Cartesian(DrawUserInterface ui, int canvasWidth, int canvasHeight, int graphPadding, String xLabel, String yLabel) {
        canvas = ui;
        width = canvasWidth;
        height = canvasHeight;
        padding = graphPadding;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }

    @Override
    public String getName(){
        return NAME;
    }

    @Override
    public void draw(ClusterRow clusters) {
        Palette palette = new Palette();
        drawBackground();
        drawClusters(clusters, palette);
    }

    private void drawBackground() {
        canvas.drawLine(padding, padding, padding + width, padding, BLACK);
        canvas.drawLine(padding, padding, padding, padding + height, BLACK);
        canvas.drawText(width, padding / 2 + TEXT_HEIGHT / 2, xLabel, BLACK);
        canvas.drawText(padding - YLABEL_OFFSET, padding + height + padding / 2 - TEXT_HEIGHT / 2, yLabel, BLACK);
    }

    private void drawClusters(ClusterRow clusters, Palette palette) {
        for (int i = 0; i < clusters.size(); i++) {
            Colour colour = palette.nextColour();
            drawCluster(clusters.clusterAt(i), colour);
        }
    }

    private void drawCluster(Cluster cluster, Colour colour) {
        UnitRow units = cluster.getUnits();
        drawUnits(units, colour);
        drawBoundingCircle(cluster, colour);
    }

    private void drawUnits(UnitRow units, Colour colour) {
        for (int i = 0; i < units.size(); i++) {
            double x = units.elementAt(i, 0);
            double y = units.elementAt(i, 1);
            plot(x, y, units.nameAt(i), colour);
        }
    }

    private void plot(double xValue, double yValue, String name, Colour colour) {
        int xPos = (int) (xValue * width);
        int yPos = (int) (yValue * height);
        canvas.drawCircle(actualPosition(xPos), actualPosition(yPos), POINT_SIZE, POINT_SIZE, colour, true);
        canvas.drawCircle(actualPosition(xPos), actualPosition(yPos), POINT_SIZE, POINT_SIZE, BLACK, false);
        canvas.setCircleHotspot(actualPosition(xPos), actualPosition(yPos), POINT_SIZE, POINT_SIZE, name);
    }

    private int actualPosition(int graphPosition) {
        return padding + graphPosition;
    }

    private void drawBoundingCircle(Cluster cluster, Colour colour) {
        UnitRow units = cluster.getUnits();
        if (units.size() == 1) {
            return;
        }

        double x = 0, y = 0;
        for (int i = 0; i < units.size(); i++) {
            x += units.elementAt(i, 0);
            y += units.elementAt(i, 1);
        }
        x /= units.size();
        y /= units.size();

        double distance = -Double.MAX_VALUE;
        for (int i = 0; i < units.size(); i++) {
            double xDIff = width * (units.elementAt(i, 0) - x);
            double yDiff = height * (units.elementAt(i, 1) - y);
            distance = Math.max(distance, Math.sqrt(Math.pow(xDIff, 2) + Math.pow(yDiff, 2)));
        }

        int xPos = (int) (x * width);
        int yPos = (int) (y * height);
        int diameter = (int) (distance * 2);
        canvas.drawCircle(actualPosition(xPos), actualPosition(yPos), diameter, diameter, colour, false);
    }
}