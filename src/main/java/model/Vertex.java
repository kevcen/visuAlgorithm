package model;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class Vertex extends JFXButton {
    public static final String styles = "-fx-background-radius: 0; -fx-border-color: black; -fx-border-width: 0.1";
    private final int row, col;
    private double hValue;
    private double gValue;


    private Vertex parentVertex;
    private boolean visited;
    private boolean isWall;

    private ObservableList<Vertex> neighbours;


    public Vertex(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        neighbours = FXCollections.observableArrayList();
        //currentState = State.UNVISITED;
        visited = false;
        isWall = false;
        hValue = 0;
        gValue = Integer.MAX_VALUE;
        setMinSize(0, 0);
        setStyle("-fx-background-color: white;" + styles);
    }

    public ObservableList<Vertex> getNeighbours() {
        return neighbours;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVisited() {
        return visited;
    }
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public double hValue() {
        return hValue;
    }
    public void setHValue(double hValue) {
        this.hValue = hValue;
    }

    public double gValue() {
        return gValue;
    }
    public void setGValue(double gValue) {
        this.gValue = gValue;
    }

    public Vertex getParentVertex() {
        return parentVertex;
    }
    public void setParentVertex(Vertex parent) {
        parentVertex = parent;
    }


    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        if(wall) {
            setStyle(styles + ";-fx-background-color: black");
        } else {
            resetStyle();
        }
        isWall = wall;
    }

    public void resetStyle() {
        setStyle(styles + ";-fx-background-color: white");
    }

    public void setStart() {
        setStyle("-fx-background-color: mediumslateblue");
    }

    public void setEnd() {
        setStyle("-fx-background-color: midnightblue");
    }
}
