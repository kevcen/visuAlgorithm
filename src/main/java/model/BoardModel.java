package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardModel {
    public static final int ROWS = 40;
    public static final int COLS = 65;

    private final ObservableList<Vertex> board;

    public BoardModel() {
        board = FXCollections.observableArrayList();

        // Add the list of vertices
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board.add(new Vertex(i, j));
            }
        }

        // Add neighbours for each vertex
        for (Vertex vertex : board) {
            for (Vertex neighbour : board) {
                if (neighbour.getCol() >= vertex.getCol() - 1 && neighbour.getCol() <= vertex.getCol() + 1
                        && neighbour.getRow() >= vertex.getRow() - 1 && neighbour.getRow() <= vertex.getRow() + 1) {
                    if (vertex != neighbour) vertex.getNeighbours().add(neighbour);
                }
            }
        }
    }

    public ObservableList<Vertex> getBoard() {
        return board;
    }
}
