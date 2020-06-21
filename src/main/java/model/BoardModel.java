package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardModel {
    public static final int ROWS = 50;
    public static final int COLS = 70;

    private final ObservableList<Vertex> board;

    public BoardModel() {
        board = FXCollections.observableArrayList();
        //enter vertices into grid

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board.add(new Vertex(i, j));
            }
        }
    }

    public ObservableList<Vertex> getBoard() {
        return board;
    }
}
