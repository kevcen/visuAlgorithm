package model;

public interface VisualiserModel {
    boolean isBarsModel();
    boolean isBoardModel();
    BarsModel asBarsModel();
    BoardModel asBoardModel();
}
