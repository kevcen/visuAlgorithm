package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Collections;

public class BarsModel implements VisualiserModel {
    public static final int NUM_OF_BARS = 500;

    //TODO: Refactor size of scene into constants
    private ObservableList<Bar> elements = FXCollections.observableArrayList();

    public static double getWidthOfBar() {
        return 900.0 / NUM_OF_BARS;
    }

    public BarsModel() {
        for (int i = 1; i <= NUM_OF_BARS; i++) {
            Bar bar = new Bar(i);
            elements.add(i - 1, bar);
        }
    }

    public ObservableList<Bar> getElements() {
        return elements;
    }



    public boolean isBarsModel() {
        return true;
    }
    public boolean isBoardModel() {
        return false;
    }
    public BarsModel asBarsModel() {
        return this;
    }
    public BoardModel asBoardModel() {
        return null;
    }
}
