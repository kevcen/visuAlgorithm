package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle {
    public static final int PADDING = 50;
    private static final int HEIGHT_SCALE = 1;

    private int value;
    private boolean visited = false;

    public Bar(int value) {
        super();
        setValue(value);
        setWidth(BarsModel.getWidthOfBar());
        setFill(Color.BLACK);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setHeight(value * HEIGHT_SCALE);
        setY(700 - getHeight() - PADDING);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public void setEnd() {
        setFill(Color.MIDNIGHTBLUE);
    }

}
