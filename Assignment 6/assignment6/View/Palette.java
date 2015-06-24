package assignment6.View;

import ui.Colour;

public class Palette {
    //The Google Material Design palette, used to provide much more good looking colours than random
    private static final int[] RED = new int[]{244, 67, 54};
    private static final int[] PINK = new int[]{233, 30, 99};
    private static final int[] PURPLE = new int[]{156, 39, 176};
    private static final int[] DEEP_PURPLE = new int[]{103, 58, 183};
    private static final int[] INDIGO = new int[]{63, 81, 181};
    private static final int[] BLUE = new int[]{33, 150, 243};
    private static final int[] LIGHT_BLUE = new int[]{3, 169, 244};
    private static final int[] CYAN = new int[]{0, 188, 212};
    private static final int[] TEAL = new int[]{0, 150, 136};
    private static final int[] GREEN = new int[]{76, 175, 80};
    private static final int[] LIGHT_GREEN = new int[]{139, 195, 74};
    private static final int[] LIME = new int[]{205, 220, 57};
    private static final int[] YELLOW = new int[]{255, 235, 59};
    private static final int[] AMBER = new int[]{255, 193, 7};
    private static final int[] ORANGE = new int[]{255, 152, 0};
    private static final int[] DEEP_ORANGE = new int[]{255, 87, 34};
    private static final int[] BROWN = new int[]{121, 85, 72};
    private static final int[] GREY = new int[]{158, 158, 158};
    private static final int[] BLUE_GREY = new int[]{96, 125, 187};
    private static final int[][] colours = new int[][]{RED, PINK, PURPLE, DEEP_PURPLE, INDIGO, BLUE, LIGHT_BLUE, CYAN, TEAL, GREEN, LIGHT_GREEN, LIME, YELLOW, AMBER, ORANGE, DEEP_ORANGE, BROWN, GREY, BLUE_GREY};

    private int i;

    Palette(){
        i=0;
    }

    public Colour nextColour() {
        if (i >= colours.length) {
            i = 0;
        }
        return colourFromRGBArray(colours[i++]);
    }

    private Colour colourFromRGBArray(int[] colourArray) {
        return new Colour(colourArray[0],colourArray[1],colourArray[2]);
    }
}
