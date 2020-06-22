package model;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BarsModel {
    public static final int NUM_OF_BARS = 10;

    private ObservableList<Bar> elements = FXCollections.observableArrayList();

    public static double getWidthOfBar() {
        return 100 / NUM_OF_BARS;
    }
}
