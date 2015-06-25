package assignment6.View;

import assignment6.Cluster.Cluster;
import assignment6.Cluster.Leaf;
import assignment6.Cluster.Node;
import assignment6.ClusterRow;
import ui.Colour;
import ui.DrawUserInterface;

public class Dendrogram implements View {
    private static final String NAME = "Dendrogram";
    private static final Colour BLACK = new Colour(0, 0, 0);
    //Reserved space for text labels
    //Because getTextWidth()'s implementation returns the same value for EVERY PIECE OF STRING
    private static final int TEXT_WIDTH = 100;
    private static final int POINT_SIZE = 10;
    int totalUnitSize, maxClusterDepth;

    private DrawUserInterface canvas;
    private int width, height, padding;

    public Dendrogram(DrawUserInterface ui, int canvasWidth, int canvasHeight, int graphPadding, int unitSize) {
        canvas = ui;
        width = canvasWidth - TEXT_WIDTH;
        height = canvasHeight;
        padding = graphPadding;
        totalUnitSize = unitSize;
        maxClusterDepth = 0;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void draw(ClusterRow clusters) {
        Palette palette = new Palette();
        int unitsDrawn = 0, y;
        //Reverse iteration order because the bigger clusters are added to the end
        //Iterating bigger clusters first makes the nodes' width consistent
        for (int i = clusters.size() - 1; i >= 0; i--) {
            Cluster cluster = clusters.clusterAt(i);
            maxClusterDepth = Math.max(maxClusterDepth, cluster.getDepth());
            y = drawCluster(cluster, unitsDrawn, palette.nextColour());
            drawLine(0, getX(cluster) - POINT_SIZE / 2, y, y, BLACK); //Leftmost line from root
            unitsDrawn += cluster.getWidth();
        }
        canvas.showChanges();
    }

    private int drawCluster(Cluster cluster, int unitsDrawn, Colour colour) {
        if (cluster.hasChildren()) {
            return drawNode((Node) cluster, unitsDrawn, colour);
        } else {
            return drawLeaf((Leaf) cluster, unitsDrawn, colour);
        }
    }

    private int drawNode(Node node, int unitsDrawn, Colour colour) {
        int x1 = getX(node.getChild1());
        int x2 = getX(node.getChild2());
        int y1 = drawCluster(node.getChild1(), unitsDrawn, colour);
        int y2 = drawCluster(node.getChild2(), unitsDrawn + node.getChild1().getWidth(), colour);
        int x = getX(node);
        int y = (y1 + y2) / 2;

        drawLine(x, x1 - POINT_SIZE / 2, y1, y1, BLACK);
        drawLine(x, x2 - POINT_SIZE / 2, y2, y2, BLACK);
        drawLine(x, x, y1, y2, BLACK);
        drawPoint(x, y, colour);
        return y;
    }

    private int drawLeaf(Leaf leaf, int unitsDrawn, Colour colour) {
        int y = (height * unitsDrawn / totalUnitSize);
        drawPoint(width, y, colour);
        drawText(width + POINT_SIZE, y - POINT_SIZE / 2, leaf.getUnits().unitAt(0).getName());
        return y;
    }

    private int getX(Cluster cluster) {
        return width - cluster.getDepth() * width / (maxClusterDepth + 1);
    }

    private void drawPoint(int x, int y, Colour colour) {
        canvas.drawCircle(actualPosition(x), actualPosition(y), POINT_SIZE, POINT_SIZE, colour, true);
        canvas.drawCircle(actualPosition(x), actualPosition(y), POINT_SIZE, POINT_SIZE, BLACK, false);
    }

    private void drawLine(int x1, int x2, int y1, int y2, Colour colour) {
        canvas.drawLine(actualPosition(x1), actualPosition(y1), actualPosition(x2), actualPosition(y2), colour);
    }

    private void drawText(int x, int y, String text) {
        canvas.drawText(actualPosition(x), actualPosition(y), text, BLACK);
    }

    private int actualPosition(int graphPosition) {
        return padding + graphPosition;
    }
}
