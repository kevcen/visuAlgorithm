package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bar extends Rectangle implements Comparable<Bar> {
    public static final int PADDING = 50;
    private int heightScale = 1;

    private int value;
    private boolean visited = false;

    public Bar(int value) {
        super();
        setValue(value);
//        setWidth(BarsModel.getWidthOfBar());
        resetStyle();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateHeight();
    }

    public void setHeightScale(int heightScale) {
        this.heightScale = heightScale;
        updateHeight();
    }

    private void updateHeight() {
        setHeight(value * heightScale);
        setY(700 - getHeight() - PADDING);
    }
    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
        setFill(Color.valueOf("#7bd1ce"));
    }

    public void setEnd() {
        setFill(Color.valueOf("#ff9012"));
    }

    public void resetStyle() {
        setFill(Color.WHITE);
    }

    public void setCurrent() {
        setFill(Color.RED);
    }

    public void setResult() {
        setFill(Color.NAVAJOWHITE);
    }

    @Override
    public int compareTo(Bar other) {
        return Integer.compare(value, other.getValue());
    }
}
