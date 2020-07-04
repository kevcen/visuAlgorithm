package model;

public interface VisualiserModel {
    double VISUALISER_WIDTH = 900.0;
    boolean isBarsModel();
    boolean isBoardModel();
    BarsModel asBarsModel();
    BoardModel asBoardModel();
}
