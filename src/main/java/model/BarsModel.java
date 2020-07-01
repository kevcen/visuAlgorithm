package model;

import controller.VisualiserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class BarsModel implements VisualiserModel {

    private ObservableList<Bar> elements = FXCollections.observableArrayList();

    public static double getWidthOfBar(int numBars) {
        return VisualiserController.VISUALISER_WIDTH / numBars;
    }

    public void setBars(int numBars) {
        for (int i = 1; i <= numBars; i++) {
            var bar = new Bar(i);
            elements.add(i - 1, bar);
        }
    }

    public ObservableList<Bar> getElements() {
        return elements;
    }

    public void randomiseBars() {
        Collections.shuffle(elements);
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
