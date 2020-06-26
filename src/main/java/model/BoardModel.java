package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BoardModel implements VisualiserModel{
    public static final int NUM_OF_FRONTIER_CELLS = 4;
    public static final int ROWS = 47;
    public static final int COLS = 67;

    private final ObservableList<Vertex> board = FXCollections.observableArrayList();

    public BoardModel() {
        // Add the list of vertices
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Vertex vertex = new Vertex(i, j);
                board.add(vertex);
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

    public Vertex getVertex(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS)
            return null;

        return board.get(row * COLS + col);
    }

    public void printMaze() {
        System.out.println("Board:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <  COLS; j++) {
                System.out.print(getVertex(i, j).isWall() ? "W " : "  ");
            }
            System.out.println();
        }
    }

    public ObservableList<Vertex> getFrontierNeighbours(Vertex vertex) {
        ObservableList<Vertex> frontierNeighbours = FXCollections.observableArrayList();
        Vertex[] currentFrontier = new Vertex[NUM_OF_FRONTIER_CELLS];
        currentFrontier[0] = getVertex(vertex.getRow() - 2, vertex.getCol());
        currentFrontier[1] = getVertex(vertex.getRow() + 2, vertex.getCol());
        currentFrontier[2] = getVertex(vertex.getRow(), vertex.getCol() - 2);
        currentFrontier[3] = getVertex(vertex.getRow(), vertex.getCol() + 2);

        for (int i = 0; i < NUM_OF_FRONTIER_CELLS; i++) {
            if (currentFrontier[i] != null) frontierNeighbours.add(currentFrontier[i]);
        }

        return frontierNeighbours;
    }


    public boolean isBarsModel() {
        return false;
    }
    public boolean isBoardModel() {
        return true;
    }
    public BarsModel asBarsModel() {
        return null;
    }
    public BoardModel asBoardModel() {
        return this;
    }
}
