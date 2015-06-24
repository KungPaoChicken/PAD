package assignment5.View;

import assignment5.ClusterRow;
import assignment5.UnitRow;
import ui.Colour;
import ui.DrawUserInterface;
import ui.UIAuxiliaryMethods;

public class Cartesian implements View {
    private static final Colour BLACK = new Colour(0, 0, 0);
    //Constant value returned by getTextHeight()
    private static final int TEXT_HEIGHT = 21;
    //Offset value for the y-axis label
    //because of getTextWidth()'s implementation that returns the same value for EVERY PIECE OF STRING
    private static final int YLABEL_OFFSET = 30;
    private static final int POINT_SIZE = 10;

    private DrawUserInterface canvas;
    private int width, height, padding;
    private String xLabel, yLabel;

    public Cartesian(DrawUserInterface ui, int width, int height, int padding, String xLabel, String yLabel) {
        canvas = ui;
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }

    @Override
    public void draw(ClusterRow clusters) {
        canvas.clear();
        drawBackground();
        for (int i = 0; i < clusters.size(); i++) {
            UnitRow units=clusters.clusterAt(i).getUnits();
            Colour colour=generateColour(units);
            drawCircle(units);
            for (int j = 0; j <units.size(); j++) {
                double x = clusters.clusterAt(i).getUnits().elementAt(j, 0);
                double y = clusters.clusterAt(i).getUnits().elementAt(j, 1);
                plot(x, y, colour);
            }
        }
        canvas.showChanges();
    }

    private void drawBackground() {
        canvas.drawLine(padding, padding, padding + width, padding, BLACK);
        canvas.drawLine(padding, padding, padding, padding + height, BLACK);
        canvas.drawText(width, padding - TEXT_HEIGHT, xLabel, BLACK);
        canvas.drawText(padding - YLABEL_OFFSET, padding + height + TEXT_HEIGHT / 2, yLabel, BLACK);
    }

    private Cartesian plot(double x, double y, Colour colour) {
        int actualX = (int) (x * width + padding);
        int actualY = (int) (y * height + padding);
        canvas.drawCircle(actualX, actualY, POINT_SIZE, POINT_SIZE, colour, true);
        canvas.drawCircle(actualX, actualY, POINT_SIZE, POINT_SIZE, BLACK, false);
        return this;
    }

    private Colour generateColour(UnitRow unit){
        Colour from = new Colour(0, 0, 0);
        Colour to = new Colour(255, 0, 0);
        return new Colour(UIAuxiliaryMethods.getRandom(0,255), UIAuxiliaryMethods.getRandom(0, 255), UIAuxiliaryMethods.getRandom(0, 255));
//        return new Colour(
//                (int) Math.round(unit.elementAt(0,0)*(to.red-from.red))+from.red,
//                (int) Math.round(unit.elementAt(0,0)*(to.green-from.green))+from.green,
//                (int) Math.round(unit.elementAt(0,0)*(to.blue-from.blue))+from.blue
//        );
    }

    private int aX(double x){
        return (int) (x * width + padding);
    }

    private int aY(double y){
        return (int) (y*height+padding);
    }

    private void drawCircle(UnitRow coordinates){
        if(coordinates.size()==2){
            int diameter=1;
            canvas.drawCircle(aX(coordinates.elementAt(0, 0)), aY(coordinates.elementAt(0, 1)), 20, 20, BLACK, false);
        }else{

        }
    }
}
